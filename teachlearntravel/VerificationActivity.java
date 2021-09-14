package com.example.teachlearntravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerificationActivity extends AppCompatActivity {


    TextView txtwe;
    EditText txtverfic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide(); // This line hide the action bar


        txtwe = findViewById(R.id.txtwelcome);
        txtverfic=findViewById(R.id.txtcode);
        txtwe.setText(CheckEmailActivity.name);

    }

    public void checkverification(View view) {
        if (Integer.parseInt(txtverfic.getText().toString())==CheckEmailActivity.n){
            startActivity(new Intent(VerificationActivity.this,UpdateActivity.class));
        }
        else{
            Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
        }

    }
}