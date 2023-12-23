package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin_AddNotice extends AppCompatActivity {
    FirebaseFirestore fBase_notice;
    EditText titleEditText_notice;
    EditText contentEditText_notice;
    ImageButton backBtn_addNotice;
    ImageButton saveNoteBtn;
    TextView AppBarTitle_AAN;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_notice);

        // Set app bar title
        AppBarTitle_AAN = findViewById(R.id.txtId_appBar);
        AppBarTitle_AAN.setText("Notice ..");

        // Set a click listener for the back button to navigate back
        backBtn_addNotice = findViewById(R.id.backBtn_1);
        backBtn_addNotice.setOnClickListener(view -> onBackPressed());

        // Initialize Firestore database instance
        fBase_notice = FirebaseFirestore.getInstance();

        // Initialize UI elements
        titleEditText_notice = findViewById(R.id.notice_title_text);
        contentEditText_notice = findViewById(R.id.notice_content_text);
        saveNoteBtn = findViewById(R.id.SaveNoticeBtnId);

        // Set a click listener for the save button
        saveNoteBtn.setOnClickListener(view -> {
            // Check if title and content are not empty
            if (titleEditText_notice.getText().toString().length() == 0) {
                titleEditText_notice.setError("Notice Title Can't be Empty..");
                titleEditText_notice.requestFocus();
            } else if (contentEditText_notice.getText().toString().length() == 0) {
                contentEditText_notice.setError("Notice Content Can't be Empty..");
                contentEditText_notice.requestFocus();
            } else {
                // Get title, content, and timestamp
                String sendTitle = titleEditText_notice.getText().toString().trim();
                String sendContent = contentEditText_notice.getText().toString().trim();
                Timestamp sendTimestamp = Timestamp.now();

                // Create a document reference and a map for the notice
                DocumentReference documentReference_notice;
                documentReference_notice = fBase_notice.collection("Notices").document();
                Map<String, Object> notice = new HashMap<>();
                notice.put("Notice_Title", sendTitle);
                notice.put("Notice_Content", sendContent);
                notice.put("Timestamp", sendTimestamp);

                // Set the notice document in the Firestore collection
                documentReference_notice.set(notice).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Notice Added Successfully ..", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(getApplicationContext(), "Notice Add Failed ..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
