package com.example.cf_srf;

public class user {
    private static String firstname;
    private static String lastname;
    private static String username;
    private static String ops_area;
    private static String empcode;
    private static String empdept;
    private static String admin;
    private static String approve;

    public user() {
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        user.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        user.lastname = lastname;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        user.username = username;
    }

    public static String getOps_area() {
        return ops_area;
    }

    public static void setOps_area(String ops_area) {
        user.ops_area = ops_area;
    }

    public static String getEmpcode() {
        return empcode;
    }

    public static void setEmpcode(String empcode) {
        user.empcode = empcode;
    }

    public static String getEmpdept() {
        return empdept;
    }

    public static void setEmpdept(String empdept) {
        user.empdept = empdept;
    }

    public static String getAdmin() {
        return admin;
    }

    public static void setAdmin(String admin) {
        user.admin = admin;
    }

    public static String getApprove() {
        return approve;
    }

    public static void setApprove(String approve) {
        user.approve = approve;
    }
}
