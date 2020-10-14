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

public class buildAndJoin_myEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_myevent);
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
    }

    public void unfinish(View v){
        Intent intent = new Intent(this,buildAndJoin_myEvent_unfinish.class);
        startActivity(intent);
    }

    public void finish(View v){
        Intent intent = new Intent(this,buildAndJoin_myEvent_finish.class);
        startActivity(intent);
    }
}
