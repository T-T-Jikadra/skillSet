package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Student_About_Us extends AppCompatActivity {
    ImageButton backBtn_StudAboutUs;
    TextView AppBarTitle_SAUs;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_about_us);

        backBtn_StudAboutUs = findViewById(R.id.backBtn_1);
        backBtn_StudAboutUs.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SAUs = findViewById(R.id.txtId_appBar);
        AppBarTitle_SAUs.setText("About Us ..");
    }
}