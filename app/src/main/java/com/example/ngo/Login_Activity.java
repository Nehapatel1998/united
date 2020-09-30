package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.ControlButton;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    private Button button,button2;
    private TextInputLayout Lay_email,Lay_password;
    private String SLay_email,SLay_password;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        firebaseAuth=FirebaseAuth.getInstance();

        Lay_email=findViewById(R.id.id_Login_Name);
        Lay_password=findViewById(R.id.id_Login_Password);

        button2=(Button)findViewById(R.id.login_button);
        button=(Button) findViewById(R.id.signup_screen_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationActivity();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SLay_email = Lay_email.getEditText().getText().toString();
                SLay_password = Lay_password.getEditText().getText().toString();


                if (!(!validateEmail() | !validatePassword()))
                {
                    firebaseAuth.signInWithEmailAndPassword(SLay_email,SLay_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login_Activity.this, "Logged In Successfully!!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else {
                                Toast.makeText(Login_Activity.this, "ERROR!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateEmail() {
        String val = Lay_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            Lay_email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            Lay_email.setError("Invalid Email!");
            return false;
        } else {
            Lay_email.setError(null);
            Lay_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Lay_password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Lay_password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 4) {
            Lay_password.setError("Password should contain 4 characters!");
            return false;
        } else {
            Lay_password.setError(null);
            Lay_password.setErrorEnabled(false);
            return true;
        }
    }
    public void openRegistrationActivity(){
        Intent intent=new Intent(Login_Activity.this,RegisterUser.class);
        startActivity(intent);
    }
}

