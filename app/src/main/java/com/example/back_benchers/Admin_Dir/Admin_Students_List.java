package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.back_benchers.Adpters.Stud_Item;
import com.example.back_benchers.Adpters.Stud_ItemAdapter;
import com.example.back_benchers.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_Students_List extends AppCompatActivity {
    ImageButton backBtn_StudentList;
    TextView title_StudListPage;
    RecyclerView recyclerView;
    Stud_ItemAdapter adapter_StudList;
    SwipeRefreshLayout swipeRefresh_StudList;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students_list);

        // Initialize Views
        // Back Button
        backBtn_StudentList = findViewById(R.id.backBtn_1);
        backBtn_StudentList.setOnClickListener(view -> onBackPressed());

        // Set ActionBar Title
        title_StudListPage = findViewById(R.id.txtId_appBar);
        title_StudListPage.setText("List Of Students ..");

        // Initialize RecyclerView and SwipeRefreshLayout
        recyclerView = findViewById(R.id.recycleView_StudList);
        swipeRefresh_StudList = findViewById(R.id.swipeRefreshLayout_StudList);

        // Configure SwipeRefreshLayout
        swipeRefresh_StudList.setOnRefreshListener(this::fetchStudentData);

        // Fetch initial data
        fetchStudentData();

        // Configure RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_StudList = new Stud_ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter_StudList);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchStudentData() {
        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference itemsRef = db.collection("Users");

        // Fetch student data from Firestore
        itemsRef.orderBy("Student_Id") // Order the data by the student_Id field
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Stud_Item> itemList = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Stud_Item item = documentSnapshot.toObject(Stud_Item.class);
                        itemList.add(item);
                    }

                    Log.d("Firestore", "Number of Items Retrieved : " + itemList.size());

                    // Update the adapter data and notify it of the change
                    adapter_StudList.updateData(itemList);
                    adapter_StudList.notifyDataSetChanged();

                    // Stop the refresh animation
                    swipeRefresh_StudList.setRefreshing(false);
                })

                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error Fetching Student Data : " + e.getMessage());
                    // Stop the refresh animation
                    swipeRefresh_StudList.setRefreshing(false);
                });
    }
}