package com.example.cf_srf;



public class status_class {
    private String status_code;
    private String status_desc;

    public status_class(String status_code, String status_desc) {
        this.status_code = status_code;
        this.status_desc = status_desc;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public static Object get(int position) {
        return null;
    }

}
