package com.marlon.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MembersActivity extends AppCompatActivity {

    private ListView listview;
    private FirebaseListAdapter<User> adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        ImageView backButton = findViewById(R.id.backButton);
        listview = findViewById(R.id.listview);

        currentUser = mAuth.getCurrentUser();

        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        displayMembers();
    }

    private void displayMembers() {
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Users").orderByChild("fullName"), User.class).setLayout(R.layout.custom_news_member).build(); // XML Layout Message.xml

        adapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull User model, int position) {
                TextView member_name = (TextView)v.findViewById(R.id.member_name);
                TextView member_email = (TextView)v.findViewById(R.id.member_email);
                TextView member_age = (TextView)v.findViewById(R.id.member_age);
                ImageView member_club_logo = (ImageView) v.findViewById(R.id.club_logo);

                member_club_logo.setImageResource(R.drawable.frats_color);
                member_name.setText(model.getFullName());
                member_email.setText("Email: " + model.getEmail());
                member_age.setText("Age: " + model.getAge());
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            adapter.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (currentUser != null) {
            adapter.stopListening();
        }
    }
}