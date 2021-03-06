package com.goku.webservice.controller.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goku.webservice.controller.BusController;
import com.goku.webservice.model.gokuBussiness;
import com.goku.webservice.model.gokuTranlogWithBLOBs;
import com.goku.webservice.service.VelocityService;
import com.goku.webservice.service.checkService;
import com.goku.webservice.service.commService;
import com.goku.webservice.util.PageUtil;
import com.goku.webservice.util.RequestUtil.RequestInfo;
import com.goku.webservice.util.RequestUtil.RequestUtil;
import com.goku.webservice.util.ResponseUtil.Body;
import com.goku.webservice.util.ResponseUtil.ResponseInfo;
import com.goku.webservice.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nbfujx on 2017-10-26.
 */
@Component
@WebService(serviceName = "BusService"
        ,targetNamespace="http://controller.webservice.goku.com/"
        ,endpointInterface = "com.goku.webservice.controller.BusController")
public class BusControllerImpl implements BusController {

    @Autowired
    private VelocityService velocityService;

    @Autowired
    private commService commservice;

    @Autowired
    private checkService checkservice;

    @Override
    public String process(String para) {

        gokuTranlogWithBLOBs gokutranlog=new gokuTranlogWithBLOBs();
        gokutranlog.setRequestxml(para.getBytes());
        gokutranlog.setCreatedate(new Date());
        try {
            RequestInfo requestInfo= RequestUtil.Serialize(XmlUtil.XMLToMap(para));
            gokutranlog.setUserid(requestInfo.getHeader().getUser_id());
            gokutranlog.setBscode(requestInfo.getHeader().getBs_code());
            gokutranlog.setTranno(requestInfo.getHeader().getTran_no());
            ResponseInfo responseInfo=new ResponseInfo();
            Body body=new Body();
            String docheckString=checkservice.checkheader(requestInfo.getHeader());  //权限验证
            if(docheckString!=null){
                body.setRet_code("1");
                body.setRet_info(docheckString);
                responseInfo.setBody(body);
            }else{
               gokuBussiness gokubus=checkservice.GetBussiness(requestInfo.getHeader().getBs_code(),requestInfo.getHeader().getTran_no());
               if("Y".equals(requestInfo.getHeader().getIs_pagination())){
                   if(requestInfo.getHeader().getPage_index()!=null&&requestInfo.getHeader().getPage_limit()!=null) {
                       int startnum = (Integer.parseInt(requestInfo.getHeader().getPage_index())-1)*Integer.parseInt(requestInfo.getHeader().getPage_limit())+1;
                       int endnum = Integer.parseInt(requestInfo.getHeader().getPage_index())*Integer.parseInt(requestInfo.getHeader().getPage_limit());
                       PageHelper.startPage(startnum, endnum);
                       Object info=null;
                        if("N".equals(gokubus.getIssqlmapper())) {
                            info=commservice.doProess(requestInfo.getHeader().getBs_code(), requestInfo.getHeader().getTran_no(), requestInfo.getBody().getData());
                        }else
                        {
                            info=velocityService.doProess(requestInfo.getHeader().getBs_code(), requestInfo.getHeader().getTran_no(),gokubus.getSqltemplate(), requestInfo.getBody().getData());
                        }
                       PageInfo page = new PageInfo((List) info);
                       body.setRet_code("0");
                       body.setRet_info("成功!");
                       body.setPage_count(Long.toString(page.getTotal()));
                       body.setPage_index(requestInfo.getHeader().getPage_index());
                       body.setData(PageUtil.Page2list((Page) info));
                       responseInfo.setBody(body);
                   }else
                   {
                       body.setRet_code("1");
                       body.setRet_info("分页参数存在问题!");
                       responseInfo.setBody(body);
                   }
               }else {
                   Object info=null;
                   if("N".equals(gokubus.getIssqlmapper())) {
                       info=commservice.doProess(requestInfo.getHeader().getBs_code(), requestInfo.getHeader().getTran_no(), requestInfo.getBody().getData());
                   }else
                   {
                       info=velocityService.doProess(requestInfo.getHeader().getBs_code(), requestInfo.getHeader().getTran_no(),gokubus.getSqltemplate(), requestInfo.getBody().getData());
                   }
                   if(info!=null) {
                       if (info.getClass().getName().equals("java.lang.Integer")) {
                           body.setRet_code("0");
                           body.setRet_info("成功!");
                           responseInfo.setBody(body);
                       }
                       else {
                           body.setRet_code("0");
                           body.setRet_info("成功!");
                           body.setData(info);
                           responseInfo.setBody(body);
                        }
                    }else {
                       body.setRet_code("0");
                       body.setRet_info("成功!");
                       responseInfo.setBody(body);
                   }
                 }
             }
            Map<String,Object> Response=new HashMap<>();
            Response.put("goku",responseInfo);
            String responseStr= XmlUtil.MapToXML(Response);
            gokutranlog.setResponsexml(responseStr.getBytes());
            gokutranlog.setSuccessflag("1");
            try {
                checkservice.SaveTranlog(gokutranlog);
            }catch (Exception ex)
            {

            }
            return responseStr;
        } catch (Exception e) {
            ResponseInfo responseInfo=new ResponseInfo();
            Body body=new Body();
            body.setRet_code("1");
            body.setRet_info(e.getMessage());
            responseInfo.setBody(body);
            Map<String,Object> Response=new HashMap<>();
            Response.put("goku",responseInfo);
            String responseStr= XmlUtil.MapToXML(Response);
            gokutranlog.setResponsexml(responseStr.getBytes());
            gokutranlog.setSuccessflag("0");
            try {
                checkservice.SaveTranlog(gokutranlog);
            }catch (Exception eex)
            {

            }
            return responseStr;
        }

    }
}
