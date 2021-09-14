package com.example.teachlearntravel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProVideoByTxtview extends AppCompatActivity {



    WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_video_by_txtview);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar
        webView = findViewById(R.id.webview);
        database db = new database();
        db.connect();
      /*  ResultSet rs = db.getdata("select * from Courses where ProfessorID ='" + Finallogin.Id + "' and CourseID='"+ HistoryLostAdapter.courseid +"'");
        try {
            if (rs.next()) {
                webView = (WebView) findViewById(R.id.webview);
                // Enable Javascript
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.getUrl(HistoryLostAdapter.videourl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      // Toast.makeText(this, ProMyCourses.courseid, Toast.LENGTH_SHORT).show();
    }*/



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }

            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Toast.makeText(ProVideoByTxtview.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(progress); //Make the bar disappear after URL is loaded
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.loadUrl(HistoryLostAdapter.videourl);         /////ابعته للطالب بالايميل////



    }
}