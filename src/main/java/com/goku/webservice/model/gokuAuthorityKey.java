package com.goku.webservice.model;

public class gokuAuthorityKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goku_authority.userid
     *
     * @mbg.generated
     */
    private String userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goku_authority.bscode
     *
     * @mbg.generated
     */
    private String bscode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goku_authority.tranno
     *
     * @mbg.generated
     */
    private String tranno;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goku_authority.userid
     *
     * @return the value of goku_authority.userid
     *
     * @mbg.generated
     */
    public String getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goku_authority.userid
     *
     * @param userid the value for goku_authority.userid
     *
     * @mbg.generated
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goku_authority.bscode
     *
     * @return the value of goku_authority.bscode
     *
     * @mbg.generated
     */
    public String getBscode() {
        return bscode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goku_authority.bscode
     *
     * @param bscode the value for goku_authority.bscode
     *
     * @mbg.generated
     */
    public void setBscode(String bscode) {
        this.bscode = bscode == null ? null : bscode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goku_authority.tranno
     *
     * @return the value of goku_authority.tranno
     *
     * @mbg.generated
     */
    public String getTranno() {
        return tranno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goku_authority.tranno
     *
     * @param tranno the value for goku_authority.tranno
     *
     * @mbg.generated
     */
    public void setTranno(String tranno) {
        this.tranno = tranno == null ? null : tranno.trim();
    }
}