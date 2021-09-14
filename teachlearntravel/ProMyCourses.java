package com.example.teachlearntravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class ProMyCourses extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<HistoryLostModel> list = new ArrayList<>();
    HistoryLostAdapter adapter;
    HistoryLostModel model;
    GetCourse gethistory;
    public static String courseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This line hide the action bar


        setContentView(R.layout.activity_pro_my_courses);

        model = new HistoryLostModel();
        gethistory = new GetCourse();

        mRecyclerView =findViewById(R.id.video_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = gethistory.GetCourse();
        adapter = new HistoryLostAdapter(list,this);
        mRecyclerView.setAdapter(adapter);




    }

  /*  @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Member>options=
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(reference,Member.class)
                        .build();
        FirebaseRecyclerAdapter<Member,ViewHolder>firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Member member) {
                        viewHolder.setVedio(getApplication(),member.getName(),member.getVideoUri());
                        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ProMyCourses.this,ProVideoByTxtview.class));
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row,parent,false);


                        return new ViewHolder(view);

                    }
                };
        firebaseRecyclerAdapter.startListening();
      //  mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }*/




}