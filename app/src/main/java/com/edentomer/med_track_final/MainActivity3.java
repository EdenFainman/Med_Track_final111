package com.edentomer.med_track_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edentomer.med_track_final.R.id;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity3 extends AppCompatActivity {

    private EditText editTextSignupFullName, editTextSignupEmail, editTextSignupDoB, editTextPwd, editTextSignupConfirmPwd;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setTitle("Sign Up");

        Toast.makeText(MainActivity3.this, "You an Sign up now", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progressBar);
        editTextSignupFullName = findViewById(R.id.editText_signup_full_name);
        editTextSignupEmail = findViewById(R.id.editText_signup_email);
        editTextSignupDoB = findViewById(R.id.editText_signup_dob);
        editTextPwd = findViewById(R.id.editText_signup_password);
        editTextSignupConfirmPwd = findViewById(R.id.editText_signup_confirm_password);


        Button buttonSignup = findViewById(id.button_Signup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFullName = editTextSignupFullName.getText().toString();
                String textEmail = editTextSignupEmail.getText().toString();
                String textDoB = editTextSignupDoB.getText().toString();
                String textPwd = editTextPwd.getText().toString();
                String textConfirmPwd = editTextSignupConfirmPwd.getText().toString();

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(MainActivity3.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextSignupFullName.setError("Full Name is required");
                    editTextSignupFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(MainActivity3.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                    editTextSignupEmail.setError("Email is required");
                    editTextSignupEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(MainActivity3.this, "Please re-enter your Email ", Toast.LENGTH_LONG).show();
                    editTextSignupEmail.setError("Valid Email is required");
                    editTextSignupEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(MainActivity3.this, "Please enter your Date of Birth", Toast.LENGTH_LONG).show();
                    editTextSignupDoB.setError("Date of birth is required");
                    editTextSignupDoB.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(MainActivity3.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextPwd.setError("Password id required");
                    editTextPwd.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(MainActivity3.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                    editTextPwd.setError("Password is too weak");
                    editTextPwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(MainActivity3.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextSignupConfirmPwd.setError("Password Confirmation required");
                    editTextSignupConfirmPwd.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(MainActivity3.this, "Please enter the same passwords", Toast.LENGTH_LONG).show();
                    editTextSignupConfirmPwd.setError("password Confirmation required");
                    editTextSignupConfirmPwd.requestFocus();
                    editTextPwd.clearComposingText();
                    editTextSignupConfirmPwd.clearComposingText();
                } else {
                    progressBar.setVisibility(view.VISIBLE);
                    signupUser(textFullName, textEmail, textDoB, textPwd);

                }
            }
        });
    }

    private void signupUser(String textFullName, String textEmail, String textDoB, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity3.this,"User registered successfully",Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    firebaseUser.sendEmailVerification();

                    Intent intent = new Intent(MainActivity3.this,UserProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
    

           


