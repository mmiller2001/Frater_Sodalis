package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasetestapplication.databinding.ActivityMainBinding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView greetings;
//    private Button logout;

    //Firebase
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseListAdapter<ChatMessage> adapter;
    private FirebaseUser user;
    private FirebaseUser currentUser;
    private DatabaseReference reference;

    // Binding
    ActivityMainBinding binding;

    //Assets
    private FloatingActionButton fab;

    private String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    // home profile settings news share logout
                    case R.id.home:
                        replaceFragment(new HomeFragment());break;
                    case R.id.profile:
                        Toast.makeText(MainActivity.this, "Profile is clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings is clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.news:
                        replaceFragment(new NewsFragment());
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share is clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                }

                return true;
            }
        });
//
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.calendar:
                    replaceFragment(new CalendarFragment());
                    break;
                case R.id.news:
                    replaceFragment(new NewsFragment());
                    break;
            }
            return true;
        });

//        logout = findViewById(R.id.signOut);
//        greetings = findViewById(R.id.greeting);
        currentUser = mAuth.getCurrentUser();

//        fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText input = findViewById(R.id.input);
//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .setValue(new ChatMessage(input.getText().toString(),
//                                FirebaseAuth.getInstance()
//                                        .getCurrentUser()
//                                        .getDisplayName())
//                        );
//
//                // Clear the input
//                input.setText("");
//            }
//        });

        if (currentUser == null)
        {
            Intent intent
                    = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        }
        else
        {
//            displayChatMessages();

            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userChat = snapshot.getValue(User.class);
                    if(userChat != null) {
                        String fullname = userChat.fullName;
                        String email = userChat.email;
                        String age = userChat.age;

//                        greetings.setText("Welcome, " + fullname + "!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Something did not go right!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

//    private void displayChatMessages() {
//        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
//
//        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(FirebaseDatabase.getInstance().getReference(), ChatMessage.class).setLayout(R.layout.message).build();
//
////                    adapter.startListening();
//        adapter = new FirebaseListAdapter<ChatMessage>(options) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("(HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (currentUser != null) {
//            adapter.startListening();
//        }
//    }
//
//    protected void onStop() {
//        super.onStop();
//        if (currentUser != null) {
//            adapter.stopListening();
//        }
//    }
}