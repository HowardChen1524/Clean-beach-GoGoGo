package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.a1061524_1061525_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class buildAndJoin_build extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_build);
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

    public void build(View v) {
        EditText event_name = (EditText) findViewById(R.id.editText_name);
        EditText event_time = (EditText) findViewById(R.id.editText_time);
        EditText event_location = (EditText) findViewById(R.id.editText_location);

        if (!TextUtils.isEmpty(event_name.getText()) && !TextUtils.isEmpty(event_time.getText()) && !TextUtils.isEmpty(event_location.getText())) {
            final String name = event_name.getText().toString();
            final String time = event_time.getText().toString();
            final String location = event_location.getText().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int eventNum = (int) dataSnapshot.child("Event").getChildrenCount();
                    myRef.child("Event").child("event" + eventNum).child("event_name").setValue(name);
                    myRef.child("Event").child("event" + eventNum).child("event_time").setValue(time);
                    myRef.child("Event").child("event" + eventNum).child("event_location").setValue(location);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    for(int i = 0; i < (int) dataSnapshot.child("User").getChildrenCount(); i++)
                        if (dataSnapshot.child("User").child("user" + i).child("user_email").getValue().equals(user.getEmail())) {
                            int join_eventNum = (int) dataSnapshot.child("User").child("user" + i).child("user_join_event").getChildrenCount();
                            myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_name").setValue(name);
                            myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_time").setValue(time);
                            myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_location").setValue(location);
                            myRef.child("User").child("user" + i).child("user_join_event").child("join_event" + join_eventNum).child("join_event_finish").setValue("unfinish");
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };
            new AlertDialog.Builder(buildAndJoin_build.this)
                    .setMessage("活動創建成功")
                    .setPositiveButton("OK", OkClick)
                    .show();
        }
        else if (TextUtils.isEmpty(event_name.getText()))
            buildState("活動名稱為空");
        else if (TextUtils.isEmpty(event_time.getText()))
            buildState("活動時間為空");
        else if (TextUtils.isEmpty(event_location.getText()))
            buildState("活動地點為空");
    }

    private void buildState(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 500);
        toast.show();
    }

}
