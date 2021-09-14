package com.example.teachlearntravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class ProMainHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar
        setContentView(R.layout.activity_pro_main_home);

        TextView proName =findViewById(R.id.proName);
      //  TextView proEmail=findViewById(R.id.proEmail);
        uploadproimg();


        SharedPreferences mPrefs = getSharedPreferences("rememberpro",0);
        String str2 = mPrefs.getString("ProfessorName","");
      //  String str = mPrefs.getString("User","");
        proName.setText(str2);
       // proEmail.setText(str);

    }


    public void logout(View view) {
        AlertDialog.Builder builder =new AlertDialog.Builder(ProMainHome.this)
                .setTitle("Log out...")
                .setIcon(R.drawable.gradelogo)
                .setMessage("Are you sure you want to Log out ?")
                .setPositiveButton("NO", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences("rememberpro",MODE_PRIVATE)
                                .edit()
                                .clear()
                                .commit();
                        startActivity(new Intent(ProMainHome.this, Finallogin.class));
                        finish();
                    }
                });

        builder.create().show();

    }


    public void uploadproimg(){
    ImageView proimg =findViewById(R.id.proimg);
        database db =new database();
        db.connect();
        ResultSet r = db.getdata("SELECT * FROM [Professor] where ProfessorID ='"+Finallogin.Id+"'");
        try {
            if (r.next()){
                StudentCategory category =new StudentCategory();

                //  StudentCategory category =new StudentCategory();
                //category.setUserImage(r.getString(2));
                // category.getUserImage(r.getString(2));
                PicassoClient.downloadImage(ProMainHome.this,r.getString(7),proimg);
                category.setUserName(r.getString(1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addcourse(View view) {

        startActivity(new Intent(ProMainHome.this,AddCourse.class));
    }

    public void mycourse(View view) {
        startActivity(new Intent(ProMainHome.this,ProMyCourses.class));
    }


}