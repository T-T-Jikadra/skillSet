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

public class Admin_ExamSchedule extends AppCompatActivity {
    // Declare UI elements and Firebase references
    GridView gridviewImages_AdminExam;
    List<Image_Item> uploadedImagesList_AdminExam;
    Button btnSelectImage_AdminExam;
    Button btnUploadImage_AdminExam;
    ImageButton backBtn_AdminExam;
    TextView AppBarTitle_AES;
    TextInputEditText edtImageName_AdminExam;
    private ImageView imgPreview_AdminExam;
    private Uri selectedImageUri_AdminExam;
    private StorageReference storeRef_AdminExam;
    private DatabaseReference databaseRef_AdminExam;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exam_schedule);

        // Set click listeners for UI elements
        backBtn_AdminExam = findViewById(R.id.backBtn_1);
        backBtn_AdminExam.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AES = findViewById(R.id.txtId_appBar);
        AppBarTitle_AES.setText("Schedule Exams ..");

        // Initialize UI elements
        edtImageName_AdminExam = findViewById(R.id.txtId_AdminExam_ImageName);
        imgPreview_AdminExam = findViewById(R.id.imgid_preview_AdminExam);
        btnSelectImage_AdminExam = findViewById(R.id.btnId_select_image_AdminExam);
        btnUploadImage_AdminExam = findViewById(R.id.btnId_upload_image_AdminExam);

        btnSelectImage_AdminExam.setOnClickListener(view -> {
            // Check if image name is provided
            if (Objects.requireNonNull(edtImageName_AdminExam.getText()).toString().isEmpty()) {
                edtImageName_AdminExam.setError("File Name Required ..");
                edtImageName_AdminExam.requestFocus();
            } else {
                selectImage_AdminExam(); // Call method to select image
            }
        });

        btnUploadImage_AdminExam.setOnClickListener(view -> uploadImage_Admin_Exam()); // Call method to upload image

        // Initialize Firebase references
        storeRef_AdminExam = FirebaseStorage.getInstance().getReference();
        databaseRef_AdminExam = FirebaseDatabase.getInstance().getReference("Exam Schedules");

        // Initialize GridView and list for uploaded images
        gridviewImages_AdminExam = findViewById(R.id.gridviewId_images_AdminExam);
        uploadedImagesList_AdminExam = new ArrayList<>();

        // Set up the adapter for the GridView
        Image_ItemAdapter imageAdapter_AdminExam = new Image_ItemAdapter(this, uploadedImagesList_AdminExam);
        gridviewImages_AdminExam.setAdapter(imageAdapter_AdminExam);

        // Load uploaded images from Firebase database
        databaseRef_AdminExam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedImagesList_AdminExam.clear();
                // Iterate through snapshots to get image data
                for (DataSnapshot imageSnapshotAdminExam : snapshot.getChildren()) {
                    Image_Item imageItem_AdminExam = imageSnapshotAdminExam.getValue(Image_Item.class);
                    uploadedImagesList_AdminExam.add(imageItem_AdminExam);
                }
                imageAdapter_AdminExam.notifyDataSetChanged(); // Update GridView
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
        // Check if an image is selected
        if (selectedImageUri_AdminExam != null) {
            final ProgressDialog progressDialogAE = new ProgressDialog(this);
            progressDialogAE.setTitle("Uploading Exam Schedule ..");
            progressDialogAE.show();

            // Get image name and reference
            String imageName = Objects.requireNonNull(edtImageName_AdminExam.getText()).toString();
            StorageReference imageReference = storeRef_AdminExam.child("Exam Schedules/" + imageName + ".jpg");

            // Upload image to storage
            imageReference.putFile(selectedImageUri_AdminExam)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri imageUrl = uriTask.getResult();

                        // Save image reference in the database
                        Image_Item imageItem_AE = new Image_Item(imageName, imageUrl.toString());
                        databaseRef_AdminExam.push().setValue(imageItem_AE);

                        Toast.makeText(this,
                                        "Exam Schedule Uploaded Successfully ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                        progressDialogAE.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this,
                                        "Exam Schedule upload failed: " + e.getMessage(),
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
            selectedImageUri_AdminExam = data.getData();
            imgPreview_AdminExam.setImageURI(selectedImageUri_AdminExam);
        }
    }
}
