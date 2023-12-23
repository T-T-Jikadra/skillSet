package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin_FeedbackDel extends AppCompatActivity {
    FirebaseFirestore FBfirestore; // Declare Firestore instance for accessing the database
    ImageButton backBtn_DelFB; // Declare ImageButton for navigating back
    Button btn_DelFB; // Declare Button for deleting feedback
    TextView AppBarTitle_FBD; // Declare TextView for displaying the app bar title

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback_del);

        // Initialize UI elements
        backBtn_DelFB = findViewById(R.id.backBtn_1);
        backBtn_DelFB.setOnClickListener(view -> onBackPressed());

        AppBarTitle_FBD = findViewById(R.id.txtId_appBar);
        AppBarTitle_FBD.setText("Feedback Data..");

        // Initialize Firestore instance
        FBfirestore = FirebaseFirestore.getInstance();

        btn_DelFB = findViewById(R.id.deleteFB);

        // Get intent from previous activity
        Intent intent_FeeedbackDel = getIntent();
        if (intent_FeeedbackDel != null) {
            // Get data from intent
            String FBdocID = intent_FeeedbackDel.getStringExtra("DocIdFB");
            Log.d("Log id ", "Admin Feedback Doc ID :  " + FBdocID);
            String FBtitleStr = intent_FeeedbackDel.getStringExtra("feedbackTitle");
            String FBsurnamestr = intent_FeeedbackDel.getStringExtra("feedbackContent");

            // Initialize TextViews to display feedback details
            TextView titleTextView = findViewById(R.id.FBTitleTextview);
            TextView contentTextView = findViewById(R.id.FBContenttextview);
            TextView docidtextView = findViewById(R.id.FBtimeTextview);

            // Set text to TextViews
            titleTextView.setText(FBtitleStr);
            contentTextView.setText(FBsurnamestr);
            docidtextView.setText(FBdocID);

            // Set click listener for delete button
            btn_DelFB.setOnClickListener(view ->
                    FBfirestore.collection("Feedback").document(FBdocID)
                            .delete()
                            .addOnCompleteListener(task -> {
                                Toast.makeText(getApplicationContext(),
                                                "Feedback Deleted .. ",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }).addOnFailureListener(e ->
                                    Toast.makeText(getApplicationContext(),
                                                    e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show()));
        }
    }
}