package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.Adpters.Image_ItemAdapter;
import com.example.back_benchers.Adpters.Image_Item;
import com.example.back_benchers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Student_ShowExams extends AppCompatActivity {
    GridView gridviewImages_StudExam;
    List<Image_Item> uploadedImagesList_StudExam;
    ImageButton backBtn_StudExam;
    TextView AppBarTitle_SSE;
    StorageReference storeRef_StudExam;
    DatabaseReference databaseRef_StudExam;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_exams);

        //BAckBtn Functionality..
        backBtn_StudExam = findViewById(R.id.backBtn_1);
        backBtn_StudExam.setOnClickListener(view -> onBackPressed());

        //Title text ..
        AppBarTitle_SSE = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSE.setText("Schedule Exams ..");

        // Initialize Firebase references
        storeRef_StudExam = FirebaseStorage.getInstance().getReference();
        databaseRef_StudExam = FirebaseDatabase.getInstance().getReference("Exam Schedules");

        // Initialize UI elements
//        edtImageName = findViewById(R.id.txtId_AdminExam_ImageName);
//        imgPreview_AdminExam = findViewById(R.id.imgid_preview_AdminExam);
//        btnSelectImage_AdminExam = findViewById(R.id.btnId_select_image_AdminExam);
//        btnUploadImage_AdminExam = findViewById(R.id.btnId_upload_image_AdminExam);
//
//        // Set click listeners
//        btnSelectImage_AdminExam.setOnClickListener(view -> selectImage_AdminExam());
//        btnUploadImage_AdminExam.setOnClickListener(view -> uploadImage_Admin_Exam());

        // Initialize GridView and list for uploaded images
        gridviewImages_StudExam = findViewById(R.id.gridviewId_imagesStudExam);
        uploadedImagesList_StudExam = new ArrayList<>();

        // Set up the adapter for the GridView
        Image_ItemAdapter imageAdapter_StudExam = new Image_ItemAdapter(this, uploadedImagesList_StudExam);
        gridviewImages_StudExam.setAdapter(imageAdapter_StudExam);

        // Load uploaded images from Firebase database
        databaseRef_StudExam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedImagesList_StudExam.clear();
                for (DataSnapshot imageSnapshotAdminExam : snapshot.getChildren()) {
                    Image_Item imageItem_AdminExam = imageSnapshotAdminExam.getValue(Image_Item.class);
                    uploadedImagesList_StudExam.add(imageItem_AdminExam);
                }
                imageAdapter_StudExam.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }
}