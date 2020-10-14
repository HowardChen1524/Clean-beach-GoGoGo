package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import com.example.a1061524_1061525_final.MapsActivity;
import com.example.a1061524_1061525_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class buildAndJoin_myEvent_unfinish_event extends AppCompatActivity {

    private int userId;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildandjoin_myevent_unfinish_event);
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
        userId = intent.getIntExtra("userId",-1);
        eventName = intent.getStringExtra("eventName");
        final TextView event_name = findViewById(R.id.text_event_name);
        event_name.setText(eventName);
    }

    public void finish(View v) {
        final EditText event_opinion = findViewById(R.id.editText_opinion);
        event_opinion.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        event_opinion.setGravity(Gravity.TOP);
        //改变默认的单行模式
        event_opinion.setSingleLine(false);
        //水平滚动设置为False
        event_opinion.setHorizontallyScrolling(false);
        if (TextUtils.isEmpty(event_opinion.getText())) {
            State("活動心得為空");
        }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                        for (int i = 0; i < (int) dataSnapshot.child("User").child("user" + userId).child("user_join_event").getChildrenCount(); i++)
                            if (dataSnapshot.child("User").child("user" + userId).child("user_join_event").child("join_event" + i).child("join_event_name").getValue().toString().equals(eventName)) {
                                int sum = Integer.parseInt(dataSnapshot.child("User").child("user" + userId).child("user_point").getValue().toString()) + 20;
                                myRef.child("User").child("user" + userId).child("user_point").setValue(sum);
                                myRef.child("User").child("user" + userId).child("user_join_event").child("join_event" + i).child("join_event_opinion").setValue(event_opinion.getText().toString());
                                myRef.child("User").child("user" + userId).child("user_join_event").child("join_event" + i).child("join_event_finish").setValue("finish");
                            }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finishParent();
                }
            };
            new AlertDialog.Builder(this)
                    .setMessage("活動完成,恭喜獲得20pt！")
                    .setPositiveButton("OK", OkClick)
                    .show();
        }
    }

    public void finishParent(){
        Intent intent = new Intent(this, buildAndJoin_myEvent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void State(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 500);
        toast.show();
    }
}
