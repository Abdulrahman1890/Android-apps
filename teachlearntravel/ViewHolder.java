/*package com.example.teachlearntravel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.storage.StorageReference;

import java.sql.Connection;
import java.sql.ResultSet;

public class ViewHolder extends RecyclerView.ViewHolder {

    SimpleExoPlayer exoPlayer;
    StorageReference videoref;
    Context c;
    TextView mTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setVedio(final Application ctx, String title,final String url){
        mTextView =itemView.findViewById(R.id.rTitleTv2);
       // VideoView videoView = itemView.findViewById(R.id.ep_video_view);
        mTextView.setText(title);
        try{
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(ctx).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(ctx);
            Uri video = Uri.parse(url);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("videos");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            database db = new database();
            Connection conn = db.connect();
            ResultSet r = db.getdata("SELECT * FROM [Courses] " );
            while (r.next()){

            }




     //       Uri uri = Uri.parse("");
       //     videoView.setVideoURI(uri);
         //   videoView.start();


    //        MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);
      //      mExoPlayerView.setPlayer(exoPlayer);
        //    exoPlayer.prepare(mediaSource);
          //  exoPlayer.setPlayWhenReady(false);
        }catch (Exception e){
            Log.e("ViewHolder","exoplayer Error"+toString());
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



}*/
