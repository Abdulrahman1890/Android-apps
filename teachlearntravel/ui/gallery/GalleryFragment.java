package com.example.teachlearntravel.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachlearntravel.Finallogin;
import com.example.teachlearntravel.GetCourse;
import com.example.teachlearntravel.HistoryLostAdapter;


import com.example.teachlearntravel.HistoryLostModel;
import com.example.teachlearntravel.R;
import com.example.teachlearntravel.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    RecyclerView mRecyclerView;
    ArrayList<HistoryLostModel> list = new ArrayList<>();
    HistoryLostAdapter adapter;
    HistoryLostModel model;
    Getallcourses gethistory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        model = new HistoryLostModel();
        gethistory = new Getallcourses();

        mRecyclerView = root.findViewById(R.id.video_recycler_view_student);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = gethistory.Getallcourses();
        adapter = new HistoryLostAdapter(list, getContext());
        mRecyclerView.setAdapter(adapter);


        return root;
    }
    
}
