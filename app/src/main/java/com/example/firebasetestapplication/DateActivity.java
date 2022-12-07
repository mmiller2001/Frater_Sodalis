package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DateActivity extends AppCompatActivity {

    private TextView selectedDate;
    private TextView fullname;

    private String global_dayNumber;
    private String global_month;
    private String global_year;
    private String global_selectedDate;

    private FirebaseUser user;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private String userID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        ImageView backButton = findViewById(R.id.backButton);
        selectedDate = findViewById(R.id.selectedDate);
        fullname = findViewById(R.id.textview_fullname);

        syncFirebase();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                global_dayNumber= null;
                global_month = null;
                global_year = null;
                global_selectedDate = null;
            } else {
                global_selectedDate = extras.getString("selectedDate");
                global_dayNumber = extras.getString("dayNumber");
                global_month = extras.getString("month");
                global_year = extras.getString("year");

                selectedDate.setText(global_selectedDate + "'s Activities");
            }
        } else {
            global_selectedDate= (String) savedInstanceState.getSerializable("selectedDate");
        }

        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void syncFirebase() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userChat = snapshot.getValue(User.class);
                if(userChat != null) {
                    fullname.setText(userChat.fullName.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DateActivity.this, "Something did not go right!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}