package com.example.ngo;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    public static final String TAG = "TAG";
    private TextInputLayout user_name, password, full_name, email, phoneNumber;
    private String Suser_name, Spassword, Sfull_name, Semail, SphoneNumber,userID;
    private FirebaseAuth firebaseAuth;
    private Button button;
    private FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_user);
        //hooks
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        user_name = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_Password);
        full_name = findViewById(R.id.reg_Login_Name);
        email = (TextInputLayout) findViewById(R.id.reg_email);
        phoneNumber = findViewById(R.id.id_phone_number);
        button=findViewById(R.id.register_button);
        //Checking User is already logged in or not
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Converting from TextInputLayout to String
                    Semail = email.getEditText().getText().toString().trim();
                    Suser_name = user_name.getEditText().getText().toString().trim();
                    Spassword = password.getEditText().getText().toString().trim();
                    Sfull_name = full_name.getEditText().getText().toString().trim();
                    SphoneNumber = phoneNumber.getEditText().getText().toString().trim();
                    if (!(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword() | !validatePhoneNumber())) {

                        firebaseAuth.createUserWithEmailAndPassword(Semail,Spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterUser.this, "Logged In Successfully!!!", Toast.LENGTH_SHORT).show();
                                    userID=firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=firebaseFirestore.collection("Users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Full_Name",Sfull_name);
                                    user.put("Phone_No",SphoneNumber);
                                    user.put("Email",Semail);
                                    user.put("User_Name",Suser_name);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess:User Profile Is Created For: "+userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else {
                                    Toast.makeText(RegisterUser.this, "ERROR!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }
            });
        }
    private boolean validateFullName() {
        String val = full_name.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            full_name.setError("Field can not be empty");
            return false;
        } else {
            full_name.setError(null);
            full_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = user_name.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            user_name.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            user_name.setError("Username is too large!");
            return false;
        } else {
            user_name.setError(null);
            user_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 4) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (val.length() != 10) {
            phoneNumber.setError("Please Enter Phone No Of 10 Digits");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}
