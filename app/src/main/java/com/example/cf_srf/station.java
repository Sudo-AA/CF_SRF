package com.example.cf_srf;



public class station{
    private String stn_code;
    private String stn_name;

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

    public station(String stn_code, String stn_name) {
        this.stn_code = stn_code;
        this.stn_name = stn_name;
    }
    public static Object get(int position) {
        return null;
    }

}
