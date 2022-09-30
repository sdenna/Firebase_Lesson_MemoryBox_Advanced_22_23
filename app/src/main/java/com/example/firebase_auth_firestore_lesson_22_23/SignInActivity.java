package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends AppCompatActivity {

    Button logInB, signUpB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        logInB = findViewById(R.id.logInButton);
        signUpB = findViewById(R.id.signUpButton);
    }

    public void logInClicked(View view) {
        // get username and password
        // check if valid with Firebase auth
        // log in user and then switch to next activity

        Intent intent = new Intent(SignInActivity.this, AddMemoryActivity.class);
        startActivity(intent);
    }


}