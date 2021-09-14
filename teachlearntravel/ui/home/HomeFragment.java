package com.example.teachlearntravel.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachlearntravel.Finallogin;
import com.example.teachlearntravel.MapsActivity;
import com.example.teachlearntravel.R;
import com.example.teachlearntravel.database;

import java.sql.Connection;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    EditText noteNameEditText,noteDescriptionEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.noNoteTextView);
        Button addNoteButton = root.findViewById(R.id.addNoteButton);
        Button saveNote=root.findViewById(R.id.saveButton);
        RecyclerView notesRecyclerView = root.findViewById(R.id.notesRecyclerView);
        noteNameEditText=root.findViewById(R.id.noteNameEditText);
        noteDescriptionEditText=root.findViewById(R.id.noteDescriptionEditText);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     Navigation.findNavController(root.getRootView())
             //           .navigate(R.id.action_addEditNotesFragment_to_nav_home);


                LayoutInflater inflaterr = LayoutInflater.from(getActivity());
                View uu = inflaterr.inflate(R.layout.add_edit_new_note,null);Dialog di = new Dialog(getActivity());
                di.setContentView(uu);
                //////////////////////uu.findview
                //////////////////////

                 Window window = di.getWindow();
                 window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                 
                di.show();


            }
        });

    /*    saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database db = new database();
                 Connection conn= db.connect();
                if(conn==null){

                }else {
                    String ss = db.rundml("insert into [Notes] values(N'" + noteNameEditText.getText() + "','" + noteDescriptionEditText.getText() + "','" + Finallogin.Id + "')");
                }

            }
        });*/

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {


            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;




    }

}