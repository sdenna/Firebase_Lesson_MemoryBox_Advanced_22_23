package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    Button logInB, signUpB;
    EditText userNameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        logInB = findViewById(R.id.logInButton);
        signUpB = findViewById(R.id.signUpButton);
        userNameET = findViewById(R.id.userNameEditText);
        passwordET = findViewById(R.id.passwordEditText);
    }

    public void logInClicked(View view) {
        getValues();        // get username and password
        // check if valid with Firebase auth
        // log in user and then switch to next activity
        Log.i("Denna", "Log in clicked");
        Intent intent = new Intent(SignInActivity.this, SelectActionActivity.class);
        startActivity(intent);
    }

    public void signUpClicked(View view) {
        getValues();        // get username and password
        // Try to create an account using auth
        // if successful, switch to next activity
        // else display an error message in a toast
        Log.i("Denna", "Sign up clicked");
    }

    private void getValues() {
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        Log.i("Denna", userName + " " + password);
    }

}