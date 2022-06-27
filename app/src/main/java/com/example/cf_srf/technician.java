package com.example.cf_srf;



public class technician {
    private String empcode;
    private String empname;
    private boolean selected;

    public technician(String empcode, String empname) {
        this.empcode = empcode;
        this.empname = empname;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public static Object get(int position) {
        return null;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return false;
    }
}
