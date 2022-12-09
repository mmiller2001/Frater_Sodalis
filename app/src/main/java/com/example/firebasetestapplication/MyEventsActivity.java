package com.example.firebasetestapplication;

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

public class MyEventsActivity extends AppCompatActivity {

    private ListView listview;
    private FirebaseListAdapter<News> adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        ImageView backButton = findViewById(R.id.backButton);
        listview = findViewById(R.id.listview);

        currentUser = mAuth.getCurrentUser();

        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyEventsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        displayMembers();
    }

    private void displayMembers() {
        FirebaseListOptions<News> options = new FirebaseListOptions.Builder<News>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Calendar/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("club_date"), News.class).setLayout(R.layout.custom_news_item).build(); // XML Layout Message.xml

        adapter = new FirebaseListAdapter<News>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull News model, int position) {
                ImageView club_logo = v.findViewById(R.id.club_logo);
                TextView club = v.findViewById(R.id.club);
                TextView club_description = v.findViewById(R.id.club_description);
                TextView title = v.findViewById(R.id.title);
                TextView description = v.findViewById(R.id.description);
                TextView time = v.findViewById(R.id.time);
                TextView date = v.findViewById(R.id.club_date);

                if(model.getClub_logo().equalsIgnoreCase("Frats")) club_logo.setImageResource(R.drawable.frats_color);
                else if(model.getClub_logo().equalsIgnoreCase("Gammas")) club_logo.setImageResource(R.drawable.gsp);
                else if(model.getClub_logo().equalsIgnoreCase("Galaxy")) club_logo.setImageResource(R.drawable.galaxy_swoosh_navy);
                else if(model.getClub_logo().equalsIgnoreCase("Kojies")) club_logo.setImageResource(R.drawable.kojies);
                else if(model.getClub_logo().equalsIgnoreCase("Siggies")) club_logo.setImageResource(R.drawable.sigma_theta_chi_graphic_2022);
                else if(model.getClub_logo().equalsIgnoreCase("Gata")) club_logo.setImageResource(R.drawable.gata);
                else if(model.getClub_logo().equalsIgnoreCase("Delta Theta")) club_logo.setImageResource(R.drawable.deltatheta);
                else if(model.getClub_logo().equalsIgnoreCase("Trojans")) club_logo.setImageResource(R.drawable.trojans);

                club.setText(model.getClub());
                club_description.setText(model.getClub_description());
                title.setText(model.getTitle());
                description.setText(model.getDescription());
                time.setText(model.getTime());
                date.setText(model.getClub_date());
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