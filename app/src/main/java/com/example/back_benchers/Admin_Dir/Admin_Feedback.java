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

import com.example.back_benchers.Adpters.feedback_Item;
import com.example.back_benchers.Adpters.feedback_ItemAdapter;
import com.example.back_benchers.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_Feedback extends AppCompatActivity {
    ImageButton backBtn_AdminFeedback;
    RecyclerView rv_feedbackShow;
    FirebaseFirestore feedback_FDB;
    TextView AppBarTitle_AFB;
    SwipeRefreshLayout swipeRefreshLayout_AdminFeedback;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);

        // Initialize UI elements
        backBtn_AdminFeedback = findViewById(R.id.backBtn_1);
        backBtn_AdminFeedback.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AFB = findViewById(R.id.txtId_appBar);
        AppBarTitle_AFB.setText("Feedback ..");

        rv_feedbackShow = findViewById(R.id.feedback_RecycleView);
        rv_feedbackShow.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout_AdminFeedback = findViewById(R.id.swipeRefreshLayout_adminFeedback);
        // Fetch feedback data from Firestore and update the RecyclerView
        swipeRefreshLayout_AdminFeedback.setOnRefreshListener(this::fetchFeedbackData);

        // Initialize Firestore instance
        feedback_FDB = FirebaseFirestore.getInstance();

        // Fetch initial feedback data
        fetchFeedbackData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchFeedbackData() {
        CollectionReference notRef = feedback_FDB.collection("Feedback");
        notRef.orderBy("Timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<feedback_Item> itemFeedback = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        feedback_Item feedback_item = documentSnapshot.toObject(feedback_Item.class);
                        itemFeedback.add(feedback_item);
                    }

                    Log.d("Firestore", "Number of Feedback Items Retrieved: " + itemFeedback.size());

                    feedback_ItemAdapter myFBadapter = new feedback_ItemAdapter(itemFeedback);
                    rv_feedbackShow.setAdapter(myFBadapter);
                    myFBadapter.notifyDataSetChanged();

                    // Hide the refreshing indicator
                    swipeRefreshLayout_AdminFeedback.setRefreshing(false);
                });
    }
}
