package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class EditMemoryActivity extends AppCompatActivity {

    TextView nameTV, descTV;
    ImageView imageIV;
    public final String TAG = "Denna";
    Memory currentMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memory);

        nameTV = findViewById(R.id.memoryNameTV);
        descTV = findViewById(R.id.memoryDescTV);
        imageIV = findViewById(R.id.memoryIV);
        imageIV.setAdjustViewBounds(true);  // preserves aspect ratio of image

        Intent intent = getIntent();
        currentMemory = intent.getParcelableExtra(ViewAllMemoriesActivity.CHOSEN_MEMORY);
        nameTV.setText(currentMemory.getName());
        descTV.setText(currentMemory.getDesc());
        imageIV.setImageURI(null);
        imageIV.setAdjustViewBounds(true);  // preserves aspect ratio of image

        Uri newUri = Uri.parse(currentMemory.getimageUri());
        imageIV.setImageURI(newUri);

        /**
         * No matter what i do, this image isn't showing up here!
         * I tried to implement Picasso, but it broke my project
         * Going to try again tomorrow when I am not so tired
         */

    }


    public void saveMemoryEdits(View v) {
        Log.d(TAG, "editing memory");
    }

    public void deleteMemory(View v) {
        Log.d(TAG, "deleting memory");
    }

    public void goBack(View v) {
        Log.d(TAG, "go back");
    }
}