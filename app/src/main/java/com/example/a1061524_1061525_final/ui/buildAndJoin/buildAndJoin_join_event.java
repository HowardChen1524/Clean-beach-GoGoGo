package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.a1061524_1061525_final.MapsActivity;
import com.example.a1061524_1061525_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class buildAndJoin_join_event extends AppCompatActivity {

    private int allEventPos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_join_event);
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
        allEventPos = intent.getIntExtra("pos",-1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        final TextView event_name = findViewById(R.id.text_event_name);
        final TextView event_time = findViewById(R.id.text_event_time);
        final TextView event_location = findViewById(R.id.text_event_location);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_event_name = dataSnapshot.child("Event").child("event" + allEventPos).child("event_name").getValue().toString();
                String str_event_time = dataSnapshot.child("Event").child("event" + allEventPos).child("event_time").getValue().toString();
                String str_event_location = dataSnapshot.child("Event").child("event" + allEventPos).child("event_location").getValue().toString();
                event_name.setText(str_event_name);
                event_time.setText(str_event_time);
                event_location.setText(str_event_location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }


    public void join(View v) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                for(int i = 0; i < (int) dataSnapshot.child("User").getChildrenCount(); i++)
                    if (dataSnapshot.child("User").child("user" + i).child("user_email").getValue().equals(user.getEmail())) {
                        int join_eventNum = (int) dataSnapshot.child("User").child("user" + i).child("user_join_event").getChildrenCount();
                        String str_event_name = dataSnapshot.child("Event").child("event" + allEventPos).child("event_name").getValue().toString();
                        String str_event_time = dataSnapshot.child("Event").child("event" + allEventPos).child("event_time").getValue().toString();
                        String str_event_location = dataSnapshot.child("Event").child("event" + allEventPos).child("event_location").getValue().toString();
                        myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_name").setValue(str_event_name);
                        myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_time").setValue(str_event_time);
                        myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_location").setValue(str_event_location);
                        myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_finish").setValue("unfinish");
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
        new AlertDialog.Builder(this)
                .setMessage("參加成功")
                .setPositiveButton("OK", OkClick)
                .show();
    }

    public void locateMap(View v){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_event_name = dataSnapshot.child("Event").child("event" + allEventPos).child("event_name").getValue().toString();
                String str_event_time = dataSnapshot.child("Event").child("event" + allEventPos).child("event_time").getValue().toString();
                String str_event_location = dataSnapshot.child("Event").child("event" + allEventPos).child("event_location").getValue().toString();
                goMap(str_event_name,str_event_time,str_event_location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void goMap(String name, String time, String location){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("time",time);
        intent.putExtra("location",location);
        startActivity(intent);
    }
}
