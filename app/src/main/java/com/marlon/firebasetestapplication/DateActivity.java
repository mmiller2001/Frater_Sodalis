package com.marlon.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DateActivity extends AppCompatActivity {

    private TextView selectedDate;
    private TextView fullname;
    private ListView listview;

    private String global_dayNumber;
    private String global_month;
    private String global_year;
    private String global_selectedDate;

    private String global_fullname;
    private int global_total = 0;

    private ArrayList<News> newsArrayList = new ArrayList<>();

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
        listview = findViewById(R.id.listview);

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

        //Firebase Creation for Calendar per getUID
        firebaseRetrievingEvents();

        //Displaying Static News Items
//        newsArrayList.add(new News("Frats","Social Club at Abilene Christian University","Frater Sodalis","Haunted Forrest","Be the first to register to Haunted Forest 2023! This year we will be having an exciting opportunity to scare you with the members of our fraternity!","8:15PM"));
//        newsArrayList.add(new News("Gammas","Social Club at Abilene Christian University","Gamma Sigma Phi","68 Hour Volleyball","Enter our exciting, non-stop, 68 hour volleyball. Lot's of fun with friends, volleyball and no rest!","3:25PM"));

        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    // obtaining Firebase Authentication and Username
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
                    global_fullname = userChat.fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DateActivity.this, "Something did not go right!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Retrieval of News Items from Firebase
    private void firebaseRetrievingEvents() {
//        News news = new News("Frats","Social Club at Abilene Christian University","Frater Sodalis","Haunted Forrest","Be the first to register to Haunted Forest 2023! This year we will be having an exciting opportunity to scare you with the members of our fraternity!","8:15PM");
//        News news2 = new News("Gammas","Social Club at Abilene Christian University","Gamma Sigma Phi","68 Hour Volleyball","Enter our exciting, non-stop, 68 hour volleyball. Lot's of fun with friends, volleyball and no rest!","3:25PM");
//        FirebaseDatabase.getInstance().getReference("Calendar" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(global_selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        global_total = (int) snapshot.getChildrenCount();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

        //Data Retrieval for One Item
        FirebaseDatabase.getInstance().getReference("Calendar" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(global_selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String test = snapshot.toString();
//                        Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
                        News myNews = snapshot.getValue(News.class);
                        if(myNews != null) {
                            newsArrayList.add(myNews);
                            ListAdapter listAdapter = new ListAdapter(DateActivity.this,newsArrayList);
                            listview.setAdapter(listAdapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//        News news3 = new News("Kojies","Social Club at Abilene Christian University","Ko Jo Kai","Marathon","Sign up for our annual Marathon. Run and support the local communities in Abilene as they battle through violence and poverty.","7:00PM","Tuesday, December 13, 2022");

//         Focuses on adding one item
//        FirebaseDatabase.getInstance().getReference("Calendar" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(global_selectedDate)
//                .setValue(news3).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Successfully added date to Calendar", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(DateActivity.this, "Registering Calendar/getUID failed", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

        // Used to create integer parents to form an array in Firebase
//        FirebaseDatabase.getInstance().getReference("Calendar" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + global_selectedDate)
//                .child(String.valueOf(global_total))
//                .setValue(news).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "getUID Calendar Registered", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Toast.makeText(DateActivity.this, "Registering Calendar/getUID failed", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
    }
}