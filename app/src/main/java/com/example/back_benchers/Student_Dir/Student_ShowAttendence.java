package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Student_ShowAttendence extends AppCompatActivity {
    ImageButton backBtn_StudAttend;
    TextView AppBarTitle_SSAttend;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_attendence);

        backBtn_StudAttend = findViewById(R.id.backBtn_1);
        backBtn_StudAttend.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SSAttend = findViewById(R.id.txtId_appBar);
        AppBarTitle_SSAttend.setText("Show Attendence ..");
    }
}