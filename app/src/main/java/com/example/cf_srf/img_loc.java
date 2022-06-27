package com.example.cf_srf;



public class img_loc {
    private String stn;
    private String srfno;
    private String page;
    private String filename;

    public img_loc(String stn, String srfno, String page, String filename) {
        this.stn = stn;
        this.srfno = srfno;
        this.page = page;
        this.filename = filename;
    }

    public String getStn() {
        return stn;
    }

    public void setStn(String stn) {
        this.stn = stn;
    }

    public String getSrfno() {
        return srfno;
    }

    public void setSrfno(String srfno) {
        this.srfno = srfno;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public static Object get(int position) {
        return null;
    }

}
