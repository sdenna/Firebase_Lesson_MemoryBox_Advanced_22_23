package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);
    }

    public void showMemories(View view) {

    }

    public void addMemories(View view) {
        Intent intent = new Intent(SelectActionActivity.this, AddMemoryActivity.class);
        startActivity(intent);
    }

    public void logOutClicked(View view) {

    }
}
