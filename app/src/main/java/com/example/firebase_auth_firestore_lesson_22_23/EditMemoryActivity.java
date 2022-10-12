package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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

        Uri newUri = Uri.parse(currentMemory.getimageUri());
        // This sometimes takes an extra second to load the image.
        // After that the images sometimes rotates horizontally for some reason???
        Picasso.with(this).load(newUri).into(imageIV);
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