package com.example.a1061524_1061525_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register_page extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
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

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "註冊完成: "+
                            user.getUid());
                    userUID =  user.getUid();
                }else{
                    Log.d("onAuthStateChanged", "尚未註冊");
                }
            }
        };
    }

    public void createUser(View v) {
        EditText ed_username = (EditText)findViewById(R.id.editText_username);
        EditText ed_email = (EditText)findViewById(R.id.editText_email);
        EditText ed_password = (EditText)findViewById(R.id.editText_password);

        if(!TextUtils.isEmpty(ed_username.getText()) && !TextUtils.isEmpty(ed_email.getText()) && !TextUtils.isEmpty(ed_password.getText())) {
            final String username = ed_username.getText().toString();
            final String email = ed_email.getText().toString();
            final String password = ed_password.getText().toString();
            auth.createUserWithEmailAndPassword(email, password)//auto login
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef = database.getReference();
                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                int userNum = (int) dataSnapshot.child("User").getChildrenCount();
                                                myRef.child("User").child("user" + userNum).child("user_name").setValue(username);
                                                myRef.child("User").child("user" + userNum).child("user_email").setValue(email);
                                                myRef.child("User").child("user" + userNum).child("user_point").setValue(0);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        registerState("註冊成功");
                                        auth.signOut();
                                        toLogin();
                                    }
                                    else {
                                        registerState("註冊失敗(信箱已存在)");
                                    }
                                }
                            });
        }
        else if (TextUtils.isEmpty(ed_username.getText()))
            registerState("用戶名為空");
        else if (TextUtils.isEmpty(ed_email.getText()))
            registerState("信箱為空");
        else if (TextUtils.isEmpty(ed_password.getText()))
            registerState("密碼為空");
    }


    private void registerState(String str){
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,500);
        toast.show();
    }

    private void toLogin(){
        Intent intent = new Intent(this,login_page.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authListener);
    }
}
