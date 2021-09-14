package com.example.teachlearntravel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HistoryLostAdapter extends  RecyclerView.Adapter<HistoryLostAdapter.HistoryLostHolder> {

    ArrayList<HistoryLostModel> list;
    Context context ;
    public static String courseid,videourl;


    public HistoryLostAdapter(ArrayList<HistoryLostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryLostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row , parent,false);
        HistoryLostHolder historyHolder = new HistoryLostHolder(view);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryLostHolder holder, int position) {
        holder.mTitle.setText(list.get(position).getTitle() );
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseid=list.get(position).getCourseid();
                videourl=list.get(position).getVideoUri();
                context.startActivity(new Intent(context,ProVideoByTxtview.class));////////
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryLostHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        public HistoryLostHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.rTitleTv2);
        }
    }

}
