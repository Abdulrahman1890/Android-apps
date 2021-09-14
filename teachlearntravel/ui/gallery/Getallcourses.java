package com.example.teachlearntravel.ui.gallery;

import android.widget.Toast;

import com.example.teachlearntravel.Finallogin;
import com.example.teachlearntravel.HistoryLostModel;
import com.example.teachlearntravel.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Getallcourses {

    public ArrayList<HistoryLostModel> Getallcourses () {
        ArrayList<HistoryLostModel> list = new ArrayList<>();


        database db = new database();
        db.connect();
        ResultSet rs = db.getdata("select * from Courses");

        try {

            while (rs.next()) {

                HistoryLostModel historyModel = new HistoryLostModel();
                historyModel.setTitle(rs.getString(1));
                historyModel.setCourseid(rs.getString(2));
                historyModel.setVideoUri(rs.getString(3));
                list.add(historyModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;
    }
}
