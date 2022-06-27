package com.example.cf_srf;



public class cat {
    private String cat_code;
    private String cat_name;

    public cat(String cat_code, String cat_name) {
        this.cat_code = cat_code;
        this.cat_name = cat_name;
    }

    public String getCat_code() {
        return cat_code;
    }

    public void setCat_code(String cat_code) {
        this.cat_code = cat_code;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public static Object get(int position) {
        return null;
    }

}
