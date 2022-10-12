package com.example.firebase_auth_firestore_lesson_22_23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddMemoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    // How to implement a Spinner
    // https://www.tutorialspoint.com/how-to-get-spinner-value-in-android

    // How to style the spinner
    // https://www.youtube.com/watch?v=7tnlh1nVkuE

    public final String TAG = "Denna";

    Spinner spinner;
    EditText memoryName, memoryDesc;
    String spinnerSelectedText = "none";

    // Necessary to upload an image

    Uri imageUri;
    String mUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Button selectImagebtn;
    ImageView selectedImage;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);

        memoryName = findViewById(R.id.memNameEditText);
        memoryDesc = findViewById(R.id.memoryDescEditText);

        db = SignInActivity.firebaseHelper.getDb();
        selectImagebtn = findViewById(R.id.selectImageButton);
        selectedImage = findViewById(R.id.memoryImageView);

        selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        // this attaches my spinner design (spinner_list.xml) and my array of spinner choices(R.array.memoryRating)
        spinner = findViewById(R.id.memorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_list,
                getResources().getStringArray(R.array.memoryRating));

        // this attaches my custom row design (how I want each row to look)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_row);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelectedText = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), spinnerSelectedText, Toast.LENGTH_SHORT).show();
    }
    // This method is required, even if empty, for the OnItemSelectedListener to work
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerSelectedText = "none selected";
    }

    public void goBackButtonClicked(View view) {
        Intent intent = new Intent(AddMemoryActivity.this, SelectActionActivity.class);
        startActivity(intent);
    }

    public void addMemoryButtonClicked(View view) {
        String memName = memoryName.getText().toString();
        String memDesc = memoryDesc.getText().toString();
        int memoryRatingNum = 0;

        // This will take the option they clicked on and ensure it is a number.
        // My options went from 5 to 1, so that is why I have it adjusted with 6-i
        // I also had an instruction statement as my first line in my string array
        // ADJUST THIS LOOP TO MATCH YOUR CODE!

        // Note the syntax here for how to access an index of a string array within
        // the java
        for (int i = 1; i < 6; i++) {
            if (spinnerSelectedText.equals(getResources().getStringArray(R.array.memoryRating)[i])) {
                memoryRatingNum = 6-i;
                break;
            }
        }

        Memory m = new Memory(memoryRatingNum, memName, memDesc);
        // uploads image to firebase storage and uses a callback interface so that
        // the code doesn't go to the next line until the image is done being uploaded.
        // after that, the uri is set (as a String) within the Memory class.
        // Lastly we add the Memory to the database
        uploadImage(m);
       // m.setimageUri(imageUri.toString());
        //Log.d(TAG, "done with setImageUri: "+ m.getimageUri());
     //   SignInActivity.firebaseHelper.addData(m);

        memoryName.setText("");
        memoryDesc.setText("");
    }

    public void addImageButtonClicked(View view) {
        selectImage();
    }

    public void uploadImage(Memory m) {
        imageUri = uploadImage(m, new StorageCallback() {
            @Override
            public void onCallback(Uri myUri) {
                Log.d(TAG, "inside onCallback: " + myUri.toString());
                imageUri = myUri;
                m.setimageUri(imageUri.toString());
                SignInActivity.firebaseHelper.addData(m);

            }
        });
    }

    private Uri uploadImage(Memory m, StorageCallback storageCallback) {
        final Uri[] uriToReturn = new Uri[1];
        // Lets user know file is being uploaded.  Shows the spinning progress circle
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();

        // String in the form of year, month, day, hours, mins, and seconds
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        // Makes the image folder unique for each authorized user because it includes the UID
        // of the user in its title
        String folderName = "images"+ SignInActivity.firebaseHelper.getmAuth().getUid() + "/";
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference(folderName+fileName);

        UploadTask uploadTask = (UploadTask) storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // removes the image from the screen display (to clear it out_
                        selectedImage.setImageURI(null);
                        Toast.makeText(AddMemoryActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();

                        // Closes the progress dialog because we are done now
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Log.d(TAG, "selectedImage.toString: " + selectedImage.toString());
                        Log.d(TAG, "storageReference: " + storageReference.getPath());
                        Log.d(TAG, ""+storageReference.getDownloadUrl());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Closes the progress dialog because we are done now
                        // even if it failed, we are still done!
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(AddMemoryActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });

        // Get a a download url
        // https://firebase.google.com/docs/storage/android/upload-files?hl=en&authuser=0

        // After the image has been uploaded, we can access the Task that was done and
        // get the download Url.
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            final Uri uriReturn = null;
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                else {
                    // Continue with the task to get the download URL
                   // return null;
                   return storageReference.getDownloadUrl();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    uriToReturn[0] = downloadUri;
                    Log.d(TAG, "downloadUri: "+downloadUri);
                    storageCallback.onCallback(downloadUri);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        return uriToReturn[0];

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if selectImage selected an image
        if (requestCode == 100 && data != null) {
            imageUri = data.getData();
            selectedImage.setImageURI(imageUri);
            Log.d(TAG, "inside onActivityResult imageUri is: " + imageUri.toString());
        }
    }

    private void getImageUri(StorageCallback storageCallback) {

    }

    // TRY this interface to see if can make the add method call pause until done getting the uri
    public interface StorageCallback {
        void onCallback(Uri myUri);
    }

}