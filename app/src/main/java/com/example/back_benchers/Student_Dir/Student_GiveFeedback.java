package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Activity for students to submit feedback
public class Student_GiveFeedback extends AppCompatActivity {
    ImageButton backBtn_StudFeedback;
    FirebaseFirestore fBase_GiveFeedback;
    EditText FeedBack_titleEditText;
    EditText FeedBack_contentEditText;
    ImageButton saveFeedBackBtn;
    TextView AppBarTitle_SGFB;
    String fb_Details_name;
    String fb_Details_sid;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_give_feedback);

        // Initialize Firestore instance
        fBase_GiveFeedback = FirebaseFirestore.getInstance();

        // Set up back button to navigate back
        backBtn_StudFeedback = findViewById(R.id.backBtn_1);
        backBtn_StudFeedback.setOnClickListener(view -> onBackPressed());

        // Set up title in the app bar
        AppBarTitle_SGFB = findViewById(R.id.txtId_appBar);
        AppBarTitle_SGFB.setText("Give Feedback ..");

        FirebaseAuth fAuth_StudFeedback = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth_StudFeedback.getCurrentUser();

        String uID = Objects.requireNonNull(firebaseUser).getEmail();
        DocumentReference userRef = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(uID));

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {

                // Retrieve user profile data from the document
                fb_Details_name = documentSnapshot.getString("Student_First_Name");
                fb_Details_sid = documentSnapshot.getString("Student_Id");

            } else {
                Toast.makeText(this,
                                "User details not found",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this,
                                "Failed to Fetch User Details ..",
                                Toast.LENGTH_SHORT)
                        .show());

        // Initialize UI elements
        FeedBack_titleEditText = findViewById(R.id.feedback_title_text);
        FeedBack_contentEditText = findViewById(R.id.feedback_content_text);
        saveFeedBackBtn = findViewById(R.id.SaveFeedbackBtnId);

        // Set up click listener for the Save Feedback button
        saveFeedBackBtn.setOnClickListener(view -> {
            if (FeedBack_titleEditText.getText().toString().length() == 0) {
                FeedBack_titleEditText.setError("Feedback Title Can't be Empty..");
                FeedBack_titleEditText.requestFocus();
            } else if (FeedBack_contentEditText.getText().toString().length() == 0) {
                FeedBack_contentEditText.setError("Feedback Content Can't be Empty..");
                FeedBack_contentEditText.requestFocus();
            } else {
                // Get feedback title, content, and timestamp
                String sendFBTitle = FeedBack_titleEditText.getText().toString().trim();
                String sendFBContent = FeedBack_contentEditText.getText().toString().trim();
                Timestamp sendFBtimestamp = Timestamp.now();

                // Reference to store feedback in Firestore
                DocumentReference documentReference_feedback;
                documentReference_feedback = fBase_GiveFeedback
                        .collection("Feedback")
                        .document(sendFBtimestamp.toDate().toString());

                // Create a map to store feedback data
                Map<String, Object> feedback = new HashMap<>();
                feedback.put("Feedback_Title", sendFBTitle);
                feedback.put("Feedback_Giver_Sid", fb_Details_sid);
                feedback.put("Feedback_Giver_Name", fb_Details_name);
                feedback.put("Feedback_Content", sendFBContent);
                feedback.put("Timestamp", sendFBtimestamp);

                // Store feedback data in Firestore
                documentReference_feedback.set(feedback).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                        "Feedback Submitted Successsfully ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                        "Feedback Add Failed ..",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
    }
}