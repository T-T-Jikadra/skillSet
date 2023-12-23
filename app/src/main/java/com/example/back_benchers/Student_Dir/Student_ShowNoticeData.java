package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Student_ShowNoticeData extends AppCompatActivity {
    FirebaseFirestore firestore_NoticeData;
    ImageButton backBtn_NoticeData;
    TextView AppBarTitle_SND;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_notice_data);

        // Initialize UI elements
        backBtn_NoticeData = findViewById(R.id.backBtn_1);
        backBtn_NoticeData.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SND = findViewById(R.id.txtId_appBar);
        AppBarTitle_SND.setText("Notice");

        // Initialize Firestore
        firestore_NoticeData = FirebaseFirestore.getInstance();

        // Get intent and retrieve data passed from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            // Get notice data from intent extras
            String DatadocID = intent.getStringExtra("noticeDocId");
            Log.d("Log id ", "Show Notice Data DocId : " + DatadocID);
            String DatatitleStr = intent.getStringExtra("noticeTitle");
            String Datasurnamestr = intent.getStringExtra("noticeContent");

            // Initialize UI elements to display notice data
            TextView titleTextView = findViewById(R.id.Notice_DataTitleTextview);
            TextView contentTextView = findViewById(R.id.Notice_DataContenttextview);
            TextView timeTextview = findViewById(R.id.Notice_DataTimeTextview);

            // Populate UI elements with notice data
            titleTextView.setText(DatatitleStr);
            contentTextView.setText(Datasurnamestr);
            timeTextview.setText(DatadocID);
        }
    }
}
