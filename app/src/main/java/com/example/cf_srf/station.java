package com.example.cf_srf;



public class station{
    private String stn_code;
    private String stn_name;
    private String  penIT;
    private String  penME;
    private String  penMT;
    private String  penTOTAL;

    public station(String stn_code, String stn_name, String penIT, String penME, String penMT, String penTOTAL) {
        this.stn_code = stn_code;
        this.stn_name = stn_name;
        this.penIT = penIT;
        this.penME = penME;
        this.penMT = penMT;
        this.penTOTAL = penTOTAL;
    }

    public String getPenIT() {
        return penIT;
    }

    public void setPenIT(String penIT) {
        this.penIT = penIT;
    }

    public String getPenME() {
        return penME;
    }

    public void setPenME(String penME) {
        this.penME = penME;
    }

    public String getPenMT() {
        return penMT;
    }

    public void setPenMT(String penMT) {
        this.penMT = penMT;
    }

    public String getPenTOTAL() {
        return penTOTAL;
    }

    public void setPenTOTAL(String penTOTAL) {
        this.penTOTAL = penTOTAL;
    }

    public void setStn_code(String stn_code) {
        this.stn_code = stn_code;
    }

    public void setStn_name(String stn_name) {
        this.stn_name = stn_name;
    }

    public String getStn_code() {
        return stn_code;
    }

    public String getStn_name() {
        return stn_name;
    }


    public static Object get(int position) {
        return null;
    }

}
