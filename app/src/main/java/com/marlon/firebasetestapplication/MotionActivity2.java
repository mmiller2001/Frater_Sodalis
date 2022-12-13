package com.marlon.firebasetestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MotionActivity2 extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion2);

        currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser == null) {
                    startActivity(new Intent(MotionActivity2.this,LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(MotionActivity2.this,MainActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}