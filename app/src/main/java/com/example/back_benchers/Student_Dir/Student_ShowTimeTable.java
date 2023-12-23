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

public class Student_ShowTimeTable extends AppCompatActivity {
    ImageButton backBtn_StudTT;
    TextView AppBarTitle_SSTT;
    GridView gridviewImages_StudTT;
    List<Image_Item> uploadedImagesList_StudTT;
    StorageReference storeRef_StudTT;
    DatabaseReference databaseRef_StudTT;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_time_table);

        // Initialize UI elements
        backBtn_StudTT = findViewById(R.id.backBtn_1);
        backBtn_StudTT.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SSTT = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSTT.setText("Show Time Table ..");

        // Initialize Firebase references
        storeRef_StudTT = FirebaseStorage.getInstance().getReference();
        databaseRef_StudTT = FirebaseDatabase.getInstance().getReference("Time Table Schedules");

        // Initialize GridView and list for uploaded images
        gridviewImages_StudTT = findViewById(R.id.gridviewId_imagesStudTT);
        uploadedImagesList_StudTT = new ArrayList<>();

        // Set up the adapter for the GridView
        Image_ItemAdapter imageAdapter_StudExam = new Image_ItemAdapter(this, uploadedImagesList_StudTT);
        gridviewImages_StudTT.setAdapter(imageAdapter_StudExam);

        // Load uploaded images from Firebase database
        databaseRef_StudTT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedImagesList_StudTT.clear();
                for (DataSnapshot imageSnapshotAdminExam : snapshot.getChildren()) {
                    Image_Item imageItem_AdminExam = imageSnapshotAdminExam.getValue(Image_Item.class);
                    uploadedImagesList_StudTT.add(imageItem_AdminExam);
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
