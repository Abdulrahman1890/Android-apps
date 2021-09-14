package com.example.teachlearntravel;

import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetCourse {

    public ArrayList<HistoryLostModel> GetCourse () {
        ArrayList<HistoryLostModel> list = new ArrayList<>();


        database db = new database();
        db.connect();
            ResultSet rs = db.getdata("select * from Courses where ProfessorID ='" + Finallogin.Id +"'");

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

