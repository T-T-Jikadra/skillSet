package com.example.back_benchers._MustHave;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.back_benchers.Admin_Dir.Admin_Login;
import com.example.back_benchers.R;
import com.example.back_benchers.Student_Dir.Student_Login;

public class select_TypeOf_User extends AppCompatActivity {
    CardView imgSelect_Admin;
    CardView imgSelect_Student;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_typeof_user);

        imgSelect_Admin = findViewById(R.id.btnAdmin);
        imgSelect_Student = findViewById(R.id.btnstud);

        imgSelect_Admin.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Admin_Login.class));
            finish();
        });

        imgSelect_Student.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Student_Login.class));
            finish();
        });
    }
}