package com.example.ngo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    public static final int PICK_IMAGE_CONSTANT=1;
    String userID;
    private Button button;
    private ProgressBar progressBar;
    private ImageView imageView;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Uri imageuri;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView Prof_username,Prof_useremail,Prof_userphoneno,Prof_fullname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Prof_username=findViewById(R.id.prof_userName);
        Prof_fullname=findViewById(R.id.prof_fullName);
        Prof_useremail=findViewById(R.id.prof_email);
        Prof_userphoneno=findViewById(R.id.prof_phoneNo);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        imageView=findViewById(R.id.prof_image);
        button=findViewById(R.id.upload_image);
        progressBar=findViewById(R.id.upload_progress_bar);
        storageReference=FirebaseStorage.getInstance().getReference("USERS");
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS");

        userID =firebaseAuth.getCurrentUser().getUid();

        StorageReference sref=storageReference.child("USERS/"+userID+"profile_image.jpg");
        sref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

        DocumentReference documentReference=firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Prof_username.setText(value.getString("User_Name"));
                Prof_fullname.setText(value.getString("Full_Name"));
                Prof_useremail.setText(value.getString("Email"));
                Prof_userphoneno.setText(value.getString("Phone_No"));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });
    }

    private void OpenFileChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_CONSTANT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CONSTANT && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageuri=data.getData();
            Picasso.get().load(imageuri).into(imageView);
           // imageView.setImageURI(imageuri); if not using picasso then this line would work the same
            uploadFile();
        }
    }
    //Code for Getting Image Extention
    /*
    private String ImageExtention()
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageuri));
    }*/
    public void uploadFile()
    {
        if (imageuri!= null)
        {
            final StorageReference filereference = storageReference.child("USERS/"+userID+"profile_image.jpg");
            filereference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //showing image from the databsae to the app
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                        }
                    });
                    //loading bar
                    Handler handler=new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },3000);
                        Toast.makeText(Profile.this,"Upload Successful",Toast.LENGTH_SHORT).show();
                        UploadImage uploadImage=new UploadImage(userID,taskSnapshot.getUploadSessionUri().toString().trim());
                        String upload= databaseReference.push().getKey();
                        databaseReference.child(upload).setValue(uploadImage);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                       double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress((int)progress);
                        }
                    });
        }
    }
}
