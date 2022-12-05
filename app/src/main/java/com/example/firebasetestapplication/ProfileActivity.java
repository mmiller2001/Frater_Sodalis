package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private String userID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView backButton = findViewById(R.id.backButton);
        Button updateButton = findViewById(R.id.update);

        EditText fullname = findViewById(R.id.fullname);
        EditText age = findViewById(R.id.age);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.passwd);

        currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {
            Intent intent
                    = new Intent(ProfileActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userChat = snapshot.getValue(User.class);
                    if(userChat != null) {
//                        String fullname = userChat.fullName;
//                        String email = userChat.email;
//                        String age = userChat.age;
                        fullname.setText(userChat.fullName.toString());
                        age.setText(userChat.age.toString());
                        email.setText(userChat.email.toString());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Something did not go right!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Button Display Action
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}