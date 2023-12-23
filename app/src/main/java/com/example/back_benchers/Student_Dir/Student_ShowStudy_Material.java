package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.back_benchers.Adpters.Assignment_Item;
import com.example.back_benchers.Adpters.Assignment_ItemAdapter;
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

public class Student_ShowStudy_Material extends AppCompatActivity {
    ImageButton backBtn_StudStudyMat;
    ListView listview_showStudyMatToStud;
    List<Assignment_Item> uploads_StudStudyMat_list;
    StorageReference storeRef_StudStudyMat;
    DatabaseReference databaseRef_StudStudyMat;
    TextView AppBarTitle_SSSM;
    SwipeRefreshLayout swipeRefresh_studStudyMat;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_study_material);

        // Initialize UI elements
        backBtn_StudStudyMat = findViewById(R.id.backBtn_1);
        backBtn_StudStudyMat.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SSSM = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSSM.setText("Show Study Materials ..");

        // Initialize Firebase Storage and Database references
        storeRef_StudStudyMat = FirebaseStorage.getInstance().getReference();
        databaseRef_StudStudyMat = FirebaseDatabase.getInstance().getReference("Study Materials");

        // Initialize ListView and data list
        listview_showStudyMatToStud = findViewById(R.id.lvId_StudStudyMat);
        uploads_StudStudyMat_list = new ArrayList<>();

        // Initialize SwipeRefreshLayout
        swipeRefresh_studStudyMat = findViewById(R.id.swipeRefreshLayout_studstudymat);
        swipeRefresh_studStudyMat.setOnRefreshListener(this::StudStudyMat_ViewAllFiles);

        // Call method to retrieve and display study materials
        StudStudyMat_ViewAllFiles();

        // Set click listener for list item
        listview_showStudyMatToStud.setOnItemClickListener(
                (adapterView, view, i, l)
                        -> {
                    Assignment_Item StudyMat_Stud = uploads_StudStudyMat_list.get(i);
                    String pdfUrl = StudyMat_Stud.getUrl();

                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
                        // Open PDF using intent
                        Intent intent_StudStudyMat = new Intent(Intent.ACTION_VIEW);
                        intent_StudStudyMat.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                        intent_StudStudyMat.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent_StudStudyMat);
                    } else {
                        // Handle the case where the PDF URL is not available
                        Toast.makeText(getApplicationContext(),
                                        "PDF URL is not available",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void StudStudyMat_ViewAllFiles() {
        // Retrieve study materials from Firebase Database
        databaseRef_StudStudyMat = FirebaseDatabase.getInstance().getReference("Study Materials");
        databaseRef_StudStudyMat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploads_StudStudyMat_list.clear(); // Clear the list before adding new data
                for (DataSnapshot SSMsnapshot : snapshot.getChildren()) {
                    // Convert DataSnapshot to Assignment_Item object
                    Assignment_Item StudStudyMat_item = SSMsnapshot.getValue(Assignment_Item.class);
                    uploads_StudStudyMat_list.add(0, StudStudyMat_item);
                }

                // Create an adapter and set it to the ListView
                Assignment_ItemAdapter adapterStud_StudyMat = new Assignment_ItemAdapter(
                        getApplicationContext(),
                        uploads_StudStudyMat_list);

                listview_showStudyMatToStud.setAdapter(adapterStud_StudyMat);

                // Stop the refreshing animation
                swipeRefresh_studStudyMat.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}