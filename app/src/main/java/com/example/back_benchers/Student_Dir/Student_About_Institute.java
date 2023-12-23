package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;

public class Student_About_Institute extends AppCompatActivity {
    ImageButton backBtn_StudAboutInst;
    TextView AppBarTitle_SAIn;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_about_institute);

        backBtn_StudAboutInst = findViewById(R.id.backBtn_1);
        backBtn_StudAboutInst.setOnClickListener(view -> onBackPressed());

        AppBarTitle_SAIn = findViewById(R.id.txtId_appBar);
        AppBarTitle_SAIn.setText("About Institute ..");
    }
}