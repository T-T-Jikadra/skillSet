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

public class Student_ShowAssignment extends AppCompatActivity {
    ImageButton backBtn_StudAss;
    ListView listview_showAssignmentToStud;
    List<Assignment_Item> uploads_StudAssign_list;
    StorageReference storeRef_StudAss;
    DatabaseReference databaseRef_StudAss;
    TextView AppBarTitle_SSAssign;
    SwipeRefreshLayout swipeRefresh_studass;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_assignment);

        // Initialize UI elements
        backBtn_StudAss = findViewById(R.id.backBtn_1);
        backBtn_StudAss.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SSAssign = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSAssign.setText("Show Asssignments ..");

        // Initialize Firebase Storage and Database references
        storeRef_StudAss = FirebaseStorage.getInstance().getReference();
        databaseRef_StudAss = FirebaseDatabase.getInstance().getReference("Assignments");

        // Initialize ListView and Assignment list
        listview_showAssignmentToStud = findViewById(R.id.lvId_StudAssign);
        uploads_StudAssign_list = new ArrayList<>();

        // Initialize SwipeRefreshLayout
        swipeRefresh_studass = findViewById(R.id.swipeRefreshLayout_studass);
        swipeRefresh_studass.setOnRefreshListener(this::StudAssign_ViewAllFiles);

        // Call method to display assignments
        StudAssign_ViewAllFiles();

        // Set item click listener for opening PDFs
        listview_showAssignmentToStud.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    Assignment_Item pdfUpload = uploads_StudAssign_list.get(i);
                    String pdfUrl = pdfUpload.getUrl();

                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
                        // Open the PDF using an intent
                        Intent intentStudShowAss = new Intent(Intent.ACTION_VIEW);
                        intentStudShowAss.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                        intentStudShowAss.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intentStudShowAss);
                    } else {
                        // Handle the case where the PDF URL is not available
                        Toast.makeText(getApplicationContext(),
                                        "PDF URL is not available",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    // Method to retrieve and display all assignments
    private void StudAssign_ViewAllFiles() {
        // Read data from Firebase Database
        databaseRef_StudAss = FirebaseDatabase.getInstance().getReference("Assignments");
        databaseRef_StudAss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploads_StudAssign_list.clear(); // Clear the list before adding new data
                for (DataSnapshot poatsnapshot : snapshot.getChildren()) {
                    Assignment_Item StudShowAss_item = poatsnapshot.getValue(Assignment_Item.class);
                    uploads_StudAssign_list.add(0, StudShowAss_item);
                }

                // Create an array for assignment names
                String[] Uploads_Array = new String[uploads_StudAssign_list.size()];
                for (int i = 0; i < Uploads_Array.length; i++) {
                    Uploads_Array[i] = uploads_StudAssign_list.get(i).getName();
                }

                // Set up the custom adapter for the ListView
                Assignment_ItemAdapter adapterStud_ShowAss = new Assignment_ItemAdapter(
                        getApplicationContext(),
                        uploads_StudAssign_list);

                listview_showAssignmentToStud.setAdapter(adapterStud_ShowAss);

                // Stop the refreshing animation
                swipeRefresh_studass.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors during data retrieval
            }
        });
    }
}
