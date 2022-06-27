package com.example.cf_srf;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_connection_adapter {
    private static String ip = "192.168.1.43";
    private static String port = "26082";
    private static String db = "CFappDatabase";
    private static String un = "sa";
    private static String password = "Cl3@nfu3l";

    public static Connection CONN() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://"+ip+":"+ port+ ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
            //Toast.makeText(getApplicationContext(),"CONNECTED",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERRO", e.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
