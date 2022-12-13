package com.marlon.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView, fullNameTextView, ageTextView;
    private Button Btn;
    private TextView alreadyRegistered;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    StorageReference storageReference;
    Uri imageUri;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        alreadyRegistered = findViewById(R.id.alreadyRegistered);

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        fullNameTextView = findViewById(R.id.fullname);
        ageTextView = findViewById(R.id.age);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();


        String fullName = fullNameTextView.getText().toString();
        String age = ageTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(fullName, age, email);

                            imageUri = Uri.parse("android.resource://"+ R.class.getPackage().getName() + "/" + R.drawable.frats_logo);
                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            storageReference = FirebaseStorage.getInstance().getReference(userID);

//                            progressDialog = new ProgressDialog(this);
//                            progressDialog.setTitle("Uploading File...");
//                            progressDialog.show();

                            storageReference.putFile(imageUri)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Toast.makeText(RegistrationActivity.this, "Successfully Uploaded Image", Toast.LENGTH_SHORT).show();
//                                                    if(progressDialog.isShowing()) {
//                                                        progressDialog.dismiss();
//                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

//                                            if(progressDialog.isShowing()) {
//                                                progressDialog.dismiss();
//                                            }
                                            Toast.makeText(RegistrationActivity.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                            FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),
                                                                "Registration successful!",
                                                                Toast.LENGTH_LONG)
                                                        .show();
                                                progressbar.setVisibility(View.GONE);

                                                // if the user created intent to login activity
                                                Intent intent
                                                        = new Intent(RegistrationActivity.this,
                                                        LoginActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(
                                                                getApplicationContext(),
                                                                "Registration failed!!"
                                                                        + " Please try again later",
                                                                Toast.LENGTH_LONG)
                                                        .show();

                                                // hide the progress bar
                                                progressbar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }

                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}