package com.example.cf_srf;



public class sra {

    private String sraDate;
    private String sraSeqNo;
    private String action;
    private String sraUserID;
    private String sraTechID;
    private String status;

    public String getSraDate() {
        return sraDate;
    }

    public void setSraDate(String sraDate) {
        this.sraDate = sraDate;
    }

    public String getSraSeqNo() {
        return sraSeqNo;
    }

    public void setSraSeqNo(String sraSeqNo) {
        this.sraSeqNo = sraSeqNo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSraUserID() {
        return sraUserID;
    }

    public void setSraUserID(String sraUserID) {
        this.sraUserID = sraUserID;
    }

    public String getSraTechID() {
        return sraTechID;
    }

    public void setSraTechID(String sraTechID) {
        this.sraTechID = sraTechID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public sra(String sraDate, String sraSeqNo, String action, String sraUserID, String sraTechID, String status) {
        this.sraDate = sraDate;
        this.sraSeqNo = sraSeqNo;
        this.action = action;
        this.sraUserID = sraUserID;
        this.sraTechID = sraTechID;
        this.status = status;
    }

    public static Object get(int position) {
        return null;
    }
}
