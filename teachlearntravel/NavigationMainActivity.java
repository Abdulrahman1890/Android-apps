package com.example.teachlearntravel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;

public class
NavigationMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();

        updateNavHeader();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.nav_username);
        TextView navUserEmail = headerView.findViewById(R.id.nav_user_email);
        ImageView navUserPhoto = headerView.findViewById(R.id.nav_user_photo);

       SharedPreferences mPrefs = getSharedPreferences("rememberstu",0);

       // String str3 = mPrefs.getString("Image","");
        //PicassoClient.downloadImage(NavigationMainActivity.this,str3,navUserPhoto);

        String str = mPrefs.getString("User","");
        String str2 = mPrefs.getString("StudentName","");

        navUserEmail.setText(str);
        navUserName.setText(str2);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.nav_username);
        TextView navUserEmail = headerView.findViewById(R.id.nav_user_email);
        ImageView navUserPhoto = headerView.findViewById(R.id.nav_user_photo);
        database db =new database();
         db.connect();
         ResultSet r = db.getdata("SELECT * FROM [Student] where StudentID ='"+Finallogin.Id+"'");
         try {
             if (r.next()){
                 StudentCategory category =new StudentCategory();
                 //category.setUserImage(r.getString(2));
                 // category.getUserImage(r.getString(2));

                 PicassoClient.downloadImage(NavigationMainActivity.this,r.getString(7),navUserPhoto);
                 category.setUserName(r.getString(1));
                 category.setUserEmail(r.getString(3));
             }
         } catch (Exception e) {
             e.printStackTrace();
         }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.Log_out){
            getSharedPreferences("rememberstu",MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();

            startActivity(new Intent(NavigationMainActivity.this,Finallogin.class));
        }

        if(id == R.id.del){

            AlertDialog.Builder m = new AlertDialog.Builder(NavigationMainActivity.this)
                    .setTitle("Delete...")
                    .setIcon(R.drawable.gradelogo)
                    .setMessage("Are you sure you want to delete your account? \uD83E\uDD7A")
                    .setPositiveButton("No", null)
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database db=new database();
                            db.connect();
                            db.rundml("delete from Student where StudentID='"+Finallogin.Id+"'");
                            getSharedPreferences("rememberstu",MODE_PRIVATE)
                                    .edit()
                                    .clear()
                                    .commit();

                            startActivity(new Intent(NavigationMainActivity.this,Finallogin.class));
                            finish();
                        }
                    });
            m.create().show();

        }

        if(id == R.id.update){
            startActivity(new Intent(NavigationMainActivity.this,UpdateStuActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }


    public void aboutus(MenuItem item) {
        startActivity(new Intent(NavigationMainActivity.this,AboutUs.class));
    }


}