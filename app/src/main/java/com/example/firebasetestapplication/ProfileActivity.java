package com.example.firebasetestapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private String userID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    StorageReference storageReference;
    StorageTask mUploadTask;

    private final int GALLERY_REQ_CODE = 1000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView backButton = findViewById(R.id.backButton);
        Button updateButton = findViewById(R.id.update);

        TextView profileName = findViewById(R.id.textview_fullname);
        TextView description = findViewById(R.id.description);
        EditText fullname = findViewById(R.id.fullname);
        EditText age = findViewById(R.id.age);
        EditText email = findViewById(R.id.email);

        imageView = findViewById(R.id.image_profile);

        // Changing Profile Picture
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

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

//            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            storageReference = FirebaseStorage.getInstance().getReference(userID);
            storageReference.child(userID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                    Uri downloadUri = taskSnapshot.get
                }
            });


            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userChat = snapshot.getValue(User.class);
                    if(userChat != null) {
                        fullname.setText(userChat.fullName.toString());
                        description.setText(userChat.email.toString());

                        profileName.setText(userChat.fullName.toString());
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



        // Back Arrow Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Update Button Firebase Connection
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mFullname = fullname.getText().toString();
                String mAge = age.getText().toString();
                String mEmail = email.getText().toString();

                updateData(mFullname, mAge, mEmail);
            }
        });
    }

    // Updating Photo from Gallery in User Profile Picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == GALLERY_REQ_CODE) {
                imageView.setImageURI(data.getData());

                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                storageReference = FirebaseStorage.getInstance().getReference(userID);

                Uri imageUri = Uri.parse(String.valueOf(imageView));

//                storageReference.child(userID).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(ProfileActivity.this, "Successfully ReUploaded Image", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ProfileActivity.this, "Couldn't save picture to Firebase", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
    }

    // Updating Text Info about User Profile
    private void updateData(String mFullname, String mAge, String mEmail) {
        HashMap User = new HashMap();
        User.put("age", mAge);
        User.put("email",mEmail);
        User.put("fullName",mFullname);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to Update Profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}