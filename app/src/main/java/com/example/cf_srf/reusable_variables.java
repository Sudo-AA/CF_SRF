package com.example.cf_srf;

public class reusable_variables {
    private static String user_firstname;
    private static String station_name;
    private static String station_code;

    public static String getUser_firstname() {
        return user_firstname;
    }

    public static void setUser_firstname(String user_firstname) {
        reusable_variables.user_firstname = user_firstname;
    }


    public static String getStation_name() {
        return station_name;
    }

    public static void setStation_name(String station_name) {
        reusable_variables.station_name = station_name;
    }

    public static String getStation_code() {
        return station_code;
    }

    public static void setStation_code(String station_code) {
        reusable_variables.station_code = station_code;
    }
}
