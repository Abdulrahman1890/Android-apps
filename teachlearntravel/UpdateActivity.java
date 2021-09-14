package com.example.teachlearntravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {


    TextView txtwe;
    EditText txtpass,txtconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide(); // This line hide the action bar

        txtwe=findViewById(R.id.txtwelcup);
        txtpass=findViewById(R.id.txtuppass);
        txtconfirm=findViewById(R.id.txtconfirm);



    }

    public void updatepass(View view) {

        if (txtpass.getText().toString().equals(txtconfirm.getText().toString())){

            database db = new database();
            db.connect();
            String ss1 = db.rundml("update Professor set password='" + txtpass.getText() + "' where email='" + CheckEmailActivity.email+"'");
            String ss2 = db.rundml("update Student set password='" + txtpass.getText() + "' where email='" + CheckEmailActivity.email+"'");
            Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }

    }
}