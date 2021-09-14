package com.example.teachlearntravel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Finallogin extends AppCompatActivity {

    TextView reg,forgetpass;
    EditText emaillogin,passlogin;
    CheckBox checkremember;
    Button login;
    RadioGroup typeradio;
    RadioButton radiopro,radiostu;
    public static String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar


        setContentView(R.layout.activity_finallogin);



        reg=findViewById(R.id.registnow);
        forgetpass=findViewById(R.id.forgetPass);
        emaillogin=findViewById(R.id.emaillogin);
        passlogin=findViewById(R.id.passlogin);
        checkremember=findViewById(R.id.checkremember);
        login=findViewById(R.id.login);
        typeradio=findViewById(R.id.typeradio);
        radiopro=findViewById(R.id.radiopro);
        radiostu=findViewById(R.id.radiostu);

        
        SharedPreferences h = getSharedPreferences("rememberpro",MODE_PRIVATE);
        String u = h.getString("User",null);
        Id=h.getString("Id",null);
        if (u!=null)
            startActivity(new Intent(Finallogin.this,ProMainHome.class));


        SharedPreferences hh = getSharedPreferences("rememberstu",MODE_PRIVATE);
        String uu = hh.getString("User",null);
        Id=hh.getString("Id",null);
        if (uu!=null)
            startActivity(new Intent(Finallogin.this,NavigationMainActivity.class));



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Finallogin.this,Finalregister.class));
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emaillogin.getText().toString().isEmpty()){
                    emaillogin.setError("Enter Email or Phone");
                    emaillogin.requestFocus();
                }
                else{
                    if (passlogin.getText().toString().isEmpty()){
                        passlogin.setError("Enter Password");
                        passlogin.requestFocus();
                    }
                    else{
                        database db = new database();
                        Connection conn = db.connect();
                        if (conn == null)
                            Toast.makeText(Finallogin.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                        else if (radiopro.isChecked() == true){

                            ResultSet r = db.getdata("select * from Professor where (Email='" + emaillogin.getText() + "' or phone='" + emaillogin.getText() + "') and password='" + passlogin.getText() + "'");
                            try {
                                if (r.next()) {
                                    if (checkremember.isChecked()){
                                        getSharedPreferences("rememberpro",MODE_PRIVATE)
                                                .edit()
                                                .putString("User",emaillogin.getText().toString())
                                                .putString("ProfessorName",r.getString(1))
                                                .putString("Image",r.getString(7))
                                                .putString("Id",r.getString(4))
                                                .commit();
                                    }
                                    Id=r.getString(4);
                                    startActivity(new Intent(Finallogin.this, ProMainHome.class));
                                    finish();
                                } else {
                                    AlertDialog.Builder m = new AlertDialog.Builder(Finallogin.this)
                                            .setTitle("Login...")
                                            .setIcon(R.drawable.gradelogo)
                                            .setMessage("Invalid Email Or Password")
                                            .setPositiveButton("Try Again", null)
                                            .setNegativeButton("Create Account", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(Finallogin.this, Finalregister.class));

                                                }
                                            });
                                    m.create().show();
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }

                        else if(radiostu.isChecked() == true){
                            ResultSet r = db.getdata("select * from Student where (Email='" + emaillogin.getText() + "' or [Phone Number]='" + emaillogin.getText() + "') and password='" + passlogin.getText() + "'");
                      //      ResultSet rr = db.getdata("select * from Gallary");

                            try {
                                if (r.next()) {
                                    if (checkremember.isChecked()){
                                        getSharedPreferences("rememberstu",MODE_PRIVATE)
                                                .edit()
                                                .putString("User",emaillogin.getText().toString())
                                                .putString("StudentName",r.getString(1))
                                                .putString("Id",r.getString(5))
                                               .putString("Image",r.getString(7))
                                                .commit();
                                    }
                                    Id=r.getString(5);
                                    startActivity(new Intent(Finallogin.this, NavigationMainActivity.class));
                                    finish();
                                } else {
                                    AlertDialog.Builder m = new AlertDialog.Builder(Finallogin.this)
                                            .setTitle("Login...")
                                            .setIcon(R.drawable.gradelogo)
                                            .setMessage("Invalid Email Or Password")
                                            .setPositiveButton("Try Again", null)
                                            .setNegativeButton("Create Account", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(Finallogin.this, Finalregister.class));
                                                }
                                            });
                                    m.create().show();
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }

                    }
                }
                          finish();
            }

        });

    }

    public void forget(View view) {
        startActivity(new Intent(Finallogin.this,CheckEmailActivity.class));
    }
}
