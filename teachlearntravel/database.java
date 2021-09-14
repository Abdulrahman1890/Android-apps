package com.example.teachlearntravel;


import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class database {
    Connection conn = null;
    public Connection connect(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://sql5092.site4now.net/DB_A6F1F1_teachapp","DB_A6F1F1_teachapp_admin","teach2010");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    public String rundml(String st){
        try {
            Statement hh = conn.createStatement();
            hh.executeUpdate(st);
            return "Done";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return throwables.getMessage();
        }
    }

    public ResultSet getdata(String st){
        ResultSet rs = null;
        try {
            Statement hh = conn.createStatement();
            rs=hh.executeQuery(st);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rs;

    }


}
