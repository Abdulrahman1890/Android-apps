package com.example.teachlearntravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CheckEmailActivity extends AppCompatActivity {

    EditText txtemail;
    public static int n;
    public static String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);
      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); // This line hide the action bar

        txtemail=findViewById(R.id.txtforgemail);
    }

    public void checkemail(View view) {


        database db = new database();
        db.connect();
        Random r=new Random();
        n=r.nextInt(9999-1111+1)-1111;

          final ResultSet rp = db.getdata("select * from Professor where Email='" + txtemail.getText() + "'");
        try {
            if (rp.next()){

                name = rp.getString(1);
                email=txtemail.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            final String username = "yourmobileapp2017@gmail.com";
                            final String password = "MMMCA@123";
                            Properties props = new Properties();
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.starttls.enable", "true");
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.port", "587");

                            Session session = Session.getInstance(props,
                                    new Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(username, password);
                                        }
                                    });

                            try {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress("yourmobileapp2017@gmail.com"));
                                message.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(txtemail.getText().toString()));

                                message.setSubject("Forget Password  - FCI Teach & Learn App-");
                                message.setText("Dear : "+rp.getString(1)+"Your Activation code is : "+n);
                                Transport.send(message);

                            } catch (MessagingException e) {
                                Toast.makeText(getApplication(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(getApplication(), "Activation code has been sent Check your Email", Toast.LENGTH_LONG).show();



                startActivity(new Intent(CheckEmailActivity.this,VerificationActivity.class));
            }
            else{
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        //////////////////////////////////////////////////////////////////////////////////////



            final ResultSet rs = db.getdata("select * from Student where Email='" + txtemail.getText() + "'");

        try {
            if (rs.next()){

                name = rs.getString(1);
                email=txtemail.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            final String username = "yourmobileapp2017@gmail.com";
                            final String password = "MMMCA@123";
                            Properties props = new Properties();
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.starttls.enable", "true");
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.port", "587");

                            Session session = Session.getInstance(props,
                                    new Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(username, password);
                                        }
                                    });

                            try {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress("yourmobileapp2017@gmail.com"));
                                message.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(txtemail.getText().toString()));

                                message.setSubject("Forget Password  - FCI Teach & Learn App-");
                                message.setText("Dear : "+rs.getString(1)+"Your Activation code is : "+n);
                                Transport.send(message);

                            } catch (MessagingException e) {
                                Toast.makeText(getApplication(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(getApplication(), "Activation code has been sent Check your Email", Toast.LENGTH_LONG).show();




                startActivity(new Intent(CheckEmailActivity.this,VerificationActivity.class));
            }
            else{
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}