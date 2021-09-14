package com.example.teachlearntravel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AddCourse extends AppCompatActivity {

    //declaration
    private static final int PICK_VIDEO_REQUEST=1;

    private VideoView videoView;
    private ProgressBar progressBar;
    private Uri videoUri;
    private EditText videoName;
    public String path;


    MediaController mediaController;

    //firebase
    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar


        Button uploadVideo = findViewById(R.id.uploadvideo);
        Button chooseVideo = findViewById(R.id.selectvideo);
        videoName=findViewById(R.id.videoname);
        videoView=findViewById(R.id.videoView);
        progressBar=findViewById(R.id.progressBar);

        mediaController = new MediaController(this);
        //firebase
        storageReference= FirebaseStorage.getInstance().getReference("videos");
        databaseReference= FirebaseDatabase.getInstance().getReference("videos");

        //videoView
        videoView.setMediaController(mediaController);

        mediaController.setAnchorView(videoView);
        videoView.start();
        chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseVideo();
            }
        });
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadVideo();


            }
        });
    }
    private void ChooseVideo(){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_VIDEO_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            videoUri=data.getData();
            videoView.setVideoURI(videoUri);
        }
    }
    private String getFileExt(Uri videoUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }
    private void UploadVideo(){
        progressBar.setVisibility(View.VISIBLE);
        if (videoUri!=null){
            StorageReference reference=storageReference.child(System.currentTimeMillis()+
                    "."+getFileExt(videoUri));
            reference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddCourse.this, "Uploaded", Toast.LENGTH_LONG).show();

                       //     HistoryLostModel member = new HistoryLostModel(videoName.getText().toString().trim(),
                         //           taskSnapshot.getUploadSessionUri().toString());
                            String upload=databaseReference.push().getKey();
                           // databaseReference.child(upload).setValue(member);
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri du = uri;
                                    path = du.toString();
                                    adddata();






                                    database db = new database();
                                    db.connect();

                                    final ResultSet rs = db.getdata("select * from Student ");
                                    try {
                                        while (rs.next()){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                final String username = "yourmobileapp2017@gmail.com";  ///////اعمل ايميل خاص بالبرنامج واقلل حمايته//////////
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
                                                    message.setFrom(new InternetAddress("yourmobileapp2017@gmail.com"));  ///////اعمل ايميل خاص بالبرنامج واقلل حمايته//////////
                                                     message.setRecipients(Message.RecipientType.TO,
                                                          InternetAddress.parse(rs.getString(3)));

                                                    message.setSubject("Hello Guys - Check your new session");
                                                    message.setText("The new session: " + HistoryLostAdapter.videourl);
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

                                }
                            } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            });




                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddCourse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Please Choose Video", Toast.LENGTH_SHORT).show();
        }

    }

    public void adddata(){
        database db = new database();
        Connection conn = db.connect();
        if (conn == null)
            //open wifi or mobile data
            Toast.makeText(AddCourse.this, "Check internet access", Toast.LENGTH_LONG).show();
        else {
            String ss = db.rundml("insert into [Courses] values(N'" + videoName.getText() + "','" + path + "','" + Finallogin.Id + "')");

        }
    }

}