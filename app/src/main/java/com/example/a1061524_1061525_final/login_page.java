package com.example.a1061524_1061525_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private EditText ed_email;
    private EditText ed_password;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //Top navigation setting
        Toolbar toolbar = findViewById(R.id.nav_topBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Button btn= (Button)findViewById(R.id.button_login);

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "登入:"+
                            user.getUid());
                    userUID =  user.getUid();
                }else{
                    Log.d("onAuthStateChanged", "已登出");
                }
            }
        };
    }

    public void login(View v){
        ed_email = (EditText)findViewById(R.id.editText_email);
        ed_password = (EditText)findViewById(R.id.editText_password);

        if(!TextUtils.isEmpty(ed_email.getText()) && !TextUtils.isEmpty(ed_password.getText())) {
            String email = ed_email.getText().toString();
            String password = ed_password.getText().toString();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginState("登入成功");
                                toMain();
                            } else {
                                loginState("登入失敗");
                            }
                        }
                    });
        }
        else if (TextUtils.isEmpty(ed_email.getText()))
            loginState("信箱為空");
        else if (TextUtils.isEmpty(ed_password.getText()))
            loginState("密碼為空");
    }

    private void loginState(String str){
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,500);
        toast.show();
    }

    private void toMain(){
        Intent intent = new Intent(this,main_page.class);
        startActivity(intent);
        ed_email.setText("");
        ed_password.setText("");
    }

    public void register(View v){
        Intent intent = new Intent(this,register_page.class);
        startActivity(intent);
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
