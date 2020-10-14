package com.example.a1061524_1061525_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class main_page extends AppCompatActivity{

    private AppBarConfiguration slide_appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        //Top navigation setting
        Toolbar toolbar = findViewById(R.id.nav_topBar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_slideBar);
        slide_appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_buildAndJoin,R.id.nav_shop, R.id.nav_contribution)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, slide_appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerLayout = navigationView.getHeaderView(0);
        final TextView username = headerLayout.findViewById(R.id.text_username);
        final TextView email = headerLayout.findViewById(R.id.text_name);
        final TextView point = headerLayout.findViewById(R.id.text_point);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                Log.d("userNum", String.valueOf(dataSnapshot.child("User").getChildrenCount()));
                for(int i = 0; i < (int) dataSnapshot.child("User").getChildrenCount(); i++)
                    if (dataSnapshot.child("User").child("user" + i).child("user_email").getValue().toString().equals(user.getEmail())) {
                        username.setText(dataSnapshot.child("User").child("user" + i).child("user_name").getValue().toString());
                        email.setText(user.getEmail());
                        point.setText(dataSnapshot.child("User").child("user" + i).child("user_point").getValue().toString());
                    }
                Log.d("userNum", String.valueOf(dataSnapshot.child("User").getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void logout(View v){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.nav_shop:
                Intent intent = new Intent(this,shop_page.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    //收回drawer
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_fragment);
        return NavigationUI.navigateUp(navController, slide_appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
        }
    }

}

