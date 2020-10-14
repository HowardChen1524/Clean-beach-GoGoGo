package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1061524_1061525_final.MapsActivity;
import com.example.a1061524_1061525_final.MyAdapter;
import com.example.a1061524_1061525_final.R;
import com.example.a1061524_1061525_final.event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class buildAndJoin_join extends AppCompatActivity {

    private RecyclerView recycler_view;
    private MyAdapter adapter;
    private  List<event> allEventData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_join);
        //Top navigation setting
        Toolbar toolbar = findViewById(R.id.nav_topBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(int i = 0; i < (int) dataSnapshot.child("Event").getChildrenCount(); i++) {
                    allEventData.add(new event(
                            dataSnapshot.child("Event").child("event" + i).child("event_name").getValue().toString(),
                            dataSnapshot.child("Event").child("event" + i).child("event_time").getValue().toString(),
                            dataSnapshot.child("Event").child("event" + i).child("event_location").getValue().toString()));
                }
                setRecycler_view();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void setRecycler_view(){
        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 將資料交給adapter
        adapter = new MyAdapter(this, new buildAndJoin_join_event(), allEventData,-1,null);
        // 設置adapter給recycler_view
        recycler_view.setAdapter(adapter);
    }
}
