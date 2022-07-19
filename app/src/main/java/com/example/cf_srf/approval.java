package com.example.cf_srf;



public class approval {
    private  String firstname;
    private  String lastname;
    private String username;
    private  String empcode;
    private  String empdept;
    private  String date;

    public approval(String firstname, String lastname, String username, String empcode, String empdept, String date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.empcode = empcode;
        this.empdept = empdept;
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getEmpdept() {
        return empdept;
    }

    public void setEmpdept(String empdept) {
        this.empdept = empdept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Object get(int position) {
        return null;
    }

}
