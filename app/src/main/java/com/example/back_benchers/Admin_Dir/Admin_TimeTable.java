package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.Adpters.Image_ItemAdapter;
import com.example.back_benchers.Adpters.Image_Item;
import com.example.back_benchers.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_TimeTable extends AppCompatActivity {
    // Declare UI elements and Firebase references
    ImageButton backBtn_AdminTT;
    TextView AppBarTitle_ATT;
    GridView gridviewImages_AdminTT;
    List<Image_Item> uploadedImagesList_AdminTT;
    Button btnSelectImage_AdminTT;
    Button btnUploadImage_AdminTT;
    TextInputEditText edtImageName_AdminTT;
    private ImageView imgPreview_AdminTT;
    private Uri selectedImageUri_AdminTT;
    private StorageReference storeRef_AdminTT;
    private DatabaseReference databaseRef_AdminTT;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_time_table);

        // Initialize Views and set click listeners
        backBtn_AdminTT = findViewById(R.id.backBtn_1);
        backBtn_AdminTT.setOnClickListener(view -> onBackPressed());

        AppBarTitle_ATT = findViewById(R.id.txtId_appBar);
        AppBarTitle_ATT.setText("Time Table ..");

        // Initialize Firebase references
        storeRef_AdminTT = FirebaseStorage.getInstance().getReference();
        databaseRef_AdminTT = FirebaseDatabase.getInstance().getReference("Time Table Schedules");

        // Initialize UI elements
        edtImageName_AdminTT = findViewById(R.id.txtId_AdminTT_ImageName);
        imgPreview_AdminTT = findViewById(R.id.imgid_preview_AdminTT);
        btnSelectImage_AdminTT = findViewById(R.id.btnId_select_image_AdminTT);
        btnUploadImage_AdminTT = findViewById(R.id.btnId_upload_image_AdminTT);

        // Set click listeners for selecting and uploading images
        btnSelectImage_AdminTT.setOnClickListener(view -> {
            if (Objects.requireNonNull(edtImageName_AdminTT.getText()).toString().isEmpty()) {
                edtImageName_AdminTT.setError("File Name Required ..");
                edtImageName_AdminTT.requestFocus();
            } else {
                selectImage_AdminExam();
            }
        });

        btnUploadImage_AdminTT.setOnClickListener(view -> uploadImage_Admin_Exam());

        // Initialize GridView and list for uploaded images
        gridviewImages_AdminTT = findViewById(R.id.gridviewId_images_AdminTT);
        uploadedImagesList_AdminTT = new ArrayList<>();

        // Set up the adapter for the GridView
        Image_ItemAdapter imageAdapter_AdminTT = new Image_ItemAdapter(this, uploadedImagesList_AdminTT);
        gridviewImages_AdminTT.setAdapter(imageAdapter_AdminTT);

        // Load uploaded images from Firebase database
        databaseRef_AdminTT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedImagesList_AdminTT.clear();
                for (DataSnapshot imageSnapshotAdminExam : snapshot.getChildren()) {
                    Image_Item imageItem_AdminTT = imageSnapshotAdminExam.getValue(Image_Item.class);
                    uploadedImagesList_AdminTT.add(imageItem_AdminTT);
                }
                imageAdapter_AdminTT.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }

    // Method to select an image from device storage
    private void selectImage_AdminExam() {
        Intent intentAE = new Intent();
        intentAE.setType("image/*");
        intentAE.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentAE, "Select Image"), 1);
    }

    // Method to upload the selected image to Firebase Storage and save its reference in the database
    private void uploadImage_Admin_Exam() {
        if (selectedImageUri_AdminTT != null) {
            final ProgressDialog progressDialogAE = new ProgressDialog(this);
            progressDialogAE.setTitle("Uploading Time Table ..");
            progressDialogAE.show();

            String imageName = Objects.requireNonNull(edtImageName_AdminTT.getText()).toString();
            StorageReference imageReference = storeRef_AdminTT.child("Time Table Schedules/" + imageName + ".jpg");

            imageReference.putFile(selectedImageUri_AdminTT)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri imageUrl = uriTask.getResult();

                        Image_Item imageItem_AE = new Image_Item(imageName, imageUrl.toString());
                        databaseRef_AdminTT.push().setValue(imageItem_AE);

                        Toast.makeText(this,
                                        "Time Table Uploaded Successfully ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                        progressDialogAE.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this,
                                        "Time Table Schedules Upload Failed: " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                        progressDialogAE.dismiss();
                    })
                    .addOnProgressListener(snapshot -> {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialogAE.setMessage("Uploading : " + (int) progress + "%");
                    });
        } else {
            Toast.makeText(this,
                            "Select an image first",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri_AdminTT = data.getData();
            imgPreview_AdminTT.setImageURI(selectedImageUri_AdminTT);
        }
    }
}