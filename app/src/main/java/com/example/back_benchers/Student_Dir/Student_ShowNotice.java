package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.back_benchers.Adpters.Notice_ItemAdapter;
import com.example.back_benchers.Adpters.notice_Item;
import com.example.back_benchers.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Student_ShowNotice extends AppCompatActivity {
    ImageButton backBtn_seeNotice;
    RecyclerView rv_noticeShow;
    ArrayList<notice_Item> uArray;
    FirebaseFirestore note_FDB;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_notice);

        // Initialize UI elements
        backBtn_seeNotice = findViewById(R.id.backBtn_1);
        backBtn_seeNotice.setOnClickListener(view -> onBackPressed());
        TextView AppBarTitle_SSN;
        AppBarTitle_SSN = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSN.setText("Show Notice ..");

        // Initialize RecyclerView
        rv_noticeShow = findViewById(R.id.notice_RecycleView);
        rv_noticeShow.setLayoutManager(new LinearLayoutManager(this));

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_studnotice);
        swipeRefreshLayout.setOnRefreshListener(this::loadNotices); // Set the refresh listener

        // Initialize Firestore and get reference to the "Notices" collection
        note_FDB = FirebaseFirestore.getInstance();
        CollectionReference notRef = note_FDB.collection("Notices");

        // Load notices initially
        loadNotices();
    }

    // Load notices from Firestore
    @SuppressLint("NotifyDataSetChanged")
    private void loadNotices() {
        // Query Firestore to retrieve notices
        note_FDB.collection("Notices")
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<notice_Item> itemnote = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        notice_Item notice_item = documentSnapshot.toObject(notice_Item.class);
                        itemnote.add(notice_item);
                    }

                    // Log the number of retrieved items
                    Log.d("Firestore", "Number of items retrieved: " + itemnote.size());

                    // Create and set up the adapter for the RecyclerView
                    Notice_ItemAdapter myad = new Notice_ItemAdapter(itemnote);
                    rv_noticeShow.setAdapter(myad);
                    myad.notifyDataSetChanged();

                    // Stop the refresh animation
                    swipeRefreshLayout.setRefreshing(false);
                });
    }
}
