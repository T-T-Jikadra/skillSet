package com.example.back_benchers.Faltu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.example.back_benchers.Student_Dir.Student_Index;
import com.google.android.material.textfield.TextInputEditText;

public class check_otp extends AppCompatActivity {
    TextInputEditText edinput_otp;
    ImageButton backBtn_otp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        backBtn_otp = findViewById(R.id.backBtn_1);

        backBtn_otp.setOnClickListener(view -> onBackPressed());

        edinput_otp = findViewById(R.id.edtxt_otp);
        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edinput_otp.getText().toString().length() < 6) {
                    edinput_otp.setError("Too Short");
                } else {
                    Toast.makeText(check_otp.this, "You've Logged In :)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(check_otp.this, Student_Index.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}