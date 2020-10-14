package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.a1061524_1061525_final.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class buildAndJoin_myEvent_finish_event extends AppCompatActivity {

    private int userId;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_myevent_finish_event);
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

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        eventName = intent.getStringExtra("eventName");
        TextView event_name = findViewById(R.id.text_event_name);
        event_name.setText(eventName);
        final TextView event_opinion = findViewById(R.id.text_event_opinion);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < (int) dataSnapshot.child("User").child("user" + userId).child("user_join_event").getChildrenCount(); i++)
                    if (dataSnapshot.child("User").child("user" + userId).child("user_join_event").child("join_event" + i).child("join_event_name").getValue().toString().equals(eventName))
                        event_opinion.setText(dataSnapshot.child("User").child("user" + userId).child("user_join_event").child("join_event" + i).child("join_event_opinion").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
