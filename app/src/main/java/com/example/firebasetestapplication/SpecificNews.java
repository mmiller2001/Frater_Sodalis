package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpecificNews extends AppCompatActivity {

    private String global_club_logo;
    private String global_club;
    private String global_club_description;
    private String global_title;
    private String global_description;
    private String global_time;
    private String global_club_date;

    private ImageView circle_profile;
    private TextView profileName;
    private TextView description;
    private TextView upperTitle;

    private TextView newsTitle;
    private TextView newsDescription;
    private TextView newsDate;
    private TextView newsTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_news);

        ImageView backButton = findViewById(R.id.backButton);
        Button toCalendar = findViewById(R.id.update);

        circle_profile = findViewById(R.id.image_profile);
        profileName = findViewById(R.id.textview_fullname);
        description = findViewById(R.id.description);
        upperTitle = findViewById(R.id.upper_title);

        newsTitle = findViewById(R.id.news_title);
        newsDescription = findViewById(R.id.news_description);
        newsDate = findViewById(R.id.news_date);
        newsTime = findViewById(R.id.news_time);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                global_club_logo= null;
                global_club = null;
                global_club_description = null;
                global_title = null;
                global_description = null;
                global_time = null;
                global_club_date = null;
            } else {
                global_club_logo = extras.getString("club_logo");
                global_club = extras.getString("club");
                global_club_description = extras.getString("club_description");
                global_title = extras.getString("title");
                global_description = extras.getString("description");
                global_time = extras.getString("time");
                global_club_date = extras.getString("club_date");
            }
        } else {
            global_club_logo = (String) savedInstanceState.getSerializable("club_logo");
            global_club = (String) savedInstanceState.getSerializable("club");
            global_club_description = (String) savedInstanceState.getSerializable("club_description");
            global_title = (String) savedInstanceState.getSerializable("title");
            global_description = (String) savedInstanceState.getSerializable("description");
            global_time = (String) savedInstanceState.getSerializable("time");
            global_club_date = (String) savedInstanceState.getSerializable("club_date");
        }

        syncInformation();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecificNews.this, MainActivity.class);
//                intent.putExtra("FragmentNum","4");
                startActivity(intent);
            }
        });

        toCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                News news3 = new News(global_club_logo,global_club_description,global_club,global_title,global_description,global_time,global_club_date);

//              Focuses on adding one item
                FirebaseDatabase.getInstance().getReference("Calendar" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(global_club_date)
                .setValue(news3).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SpecificNews.this, "Successfully added Event to Calendar", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(SpecificNews.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void syncInformation() {
        if(global_club_logo.equalsIgnoreCase("Frats")) circle_profile.setImageResource(R.drawable.frats_color);
        else if(global_club_logo.equalsIgnoreCase("Gammas")) circle_profile.setImageResource(R.drawable.gsp);
        else if(global_club_logo.equalsIgnoreCase("Galaxy")) circle_profile.setImageResource(R.drawable.galaxy_swoosh_navy);
        else if(global_club_logo.equalsIgnoreCase("Kojies")) circle_profile.setImageResource(R.drawable.kojies);
        else if(global_club_logo.equalsIgnoreCase("Siggies")) circle_profile.setImageResource(R.drawable.sigma_theta_chi_graphic_2022);
        else if(global_club_logo.equalsIgnoreCase("Gata")) circle_profile.setImageResource(R.drawable.gata);
        else if(global_club_logo.equalsIgnoreCase("Delta Theta")) circle_profile.setImageResource(R.drawable.deltatheta);
        else if(global_club_logo.equalsIgnoreCase("Trojans")) circle_profile.setImageResource(R.drawable.trojans);

        profileName.setText(global_club);
        description.setText(global_club_description);
        upperTitle.setText(global_club + " - " + global_time);

        newsTitle.setText(global_title);
        newsDescription.setText(global_description);
        newsDate.setText(global_club_date);
        newsTime.setText(global_time);
    }
}