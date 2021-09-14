package com.example.teachlearntravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.sql.SQLException;

public class UpdateStuActivity extends AppCompatActivity {


    EditText fullname,emailaddress,password,phone;
    RadioGroup gender;
    RadioButton genderf,genderm;
    ImageView imgm,imgf,map,acountimg;
    Button regist;
    Uri uri;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stu);

      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar


        fullname=findViewById(R.id.fullNamep);
        emailaddress=findViewById(R.id.emailAddressp);
        password=findViewById(R.id.passwordp);
        phone=findViewById(R.id.phonep);
        gender=findViewById(R.id.genderp);
        genderf=findViewById(R.id.genderfp);
        genderm=findViewById(R.id.gendermp);
        imgm=findViewById(R.id.imgmp);
        imgf=findViewById(R.id.imgfp);
        map=findViewById(R.id.mapp);
        regist=findViewById(R.id.cregisterp);
        //add photo
        acountimg=findViewById(R.id.acountimgp);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimg();
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String passStyle = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{8,10}$";
                    if (password.getText().toString().matches(passStyle));
                    else {
                        Toast.makeText(UpdateStuActivity.this, "Note - password is weak!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        emailaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String emStyle = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                if (emailaddress.getText().toString().matches(emStyle)) ;
                else {
                    emailaddress.setError("Wrong Email Format");
                }
            }
        });

        database db=new database();
        db.connect();
        ResultSet rs=db.getdata("select * from Student where StudentID='"+Finallogin.Id+"'");////////////////////////

        try {
            if(rs.next()){
                fullname.setText(rs.getString(1));
                phone.setText(rs.getString(2));
                emailaddress.setText(rs.getString(3));
                password.setText(rs.getString(6));
                acountimg.setImageURI(Uri.parse(rs.getString(7)));
                if (rs.getString(4)=="Male")
                    genderm.setChecked(true);
                else
                    genderf.setChecked(true);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void uploadimg(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading please wait");
        pd.show();
        FirebaseStorage fs =  FirebaseStorage.getInstance();
        StorageReference sr = fs.getReference();
        final StorageReference rf = sr.child("user photo/" + acountimg.getDrawable());
        rf.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        rf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri du = uri;
                                pd.dismiss();
                                path = du.toString();
                                update();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        pd.dismiss();
                        Toast.makeText(UpdateStuActivity.this, "Failed"+ exception.getMessage(), Toast.LENGTH_SHORT).show();
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("upload"+(int)progress+"%");
            }
        });

    }
    public void update() {

        if (fullname.getText().toString().isEmpty()) {
            fullname.setError("Enter Name");
            fullname.requestFocus();
        } else {
            if (emailaddress.getText().toString().isEmpty()) {
                emailaddress.setError("Enter Email");
                emailaddress.requestFocus();
            } else {
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Enter Phone Number");
                    phone.requestFocus();
                } else {
                    if (password.getText().toString().isEmpty()) {
                        password.setError("Enter Password");
                        password.requestFocus();
                    } else {
                        database db = new database();
                        Connection conn = db.connect();
                        if (conn == null)
                            //open wifi or mobile data
                            Toast.makeText(this, "Check internet access", Toast.LENGTH_LONG).show();
                        else {
                            String gen = "Male";
                            if (genderf.isChecked())
                                gen = "Female";

                            String ss = db.rundml("update [Student] set [Student Name]=N'" + fullname.getText() + "',[Phone Number]='" + phone.getText() + "',Email='" + emailaddress.getText() + "',Password='" + password.getText() + "',Image='" + path + "',latitude='" + MapsActivity.l + "',longtude='" + MapsActivity.g + "' where StudentID='"+Finallogin.Id+"'");
                            if (ss.equals("Done")) {
                                AlertDialog.Builder m = new AlertDialog.Builder(UpdateStuActivity.this)
                                        .setTitle("Updated...")
                                        .setIcon(R.drawable.gradelogo)
                                        .setMessage("Your account has been updated")
                                        .setPositiveButton("Thanks ♥️", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(UpdateStuActivity.this,Finallogin.class));
                                            }
                                        });
                                m.create().show();
                            } else if (ss.contains("UQEmail"))
                                Toast.makeText(this, "This email is used", Toast.LENGTH_SHORT).show();
                            else if (ss.contains("UQPhone"))
                                Toast.makeText(this, "This phone is used", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "" + ss, Toast.LENGTH_LONG).show();

                        }

                    }
                }
            }
        }




    }

    public void addphoto(View view) {
        Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==100 && resultCode==RESULT_OK && data!=null && data.getData() != null){
            uri =data.getData();
            acountimg.setImageURI(uri);
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),uri);
                acountimg.setImageBitmap(bm);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public void gotomap(View view) {
        startActivity(new Intent(UpdateStuActivity.this,MapsActivity.class));
    }



}