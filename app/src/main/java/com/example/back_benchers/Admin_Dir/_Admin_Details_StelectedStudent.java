package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class _Admin_Details_StelectedStudent extends AppCompatActivity {
    ImageButton backBtn_details;
    Button update;
    Button del;
    FirebaseFirestore db;
    TextView AppBarTitle_ADSS;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_admin_details_stelectedstudent);

        db = FirebaseFirestore.getInstance();

        backBtn_details = findViewById(R.id.backBtn_1);
        backBtn_details.setOnClickListener(view -> onBackPressed());

        AppBarTitle_ADSS = findViewById(R.id.txtId_appBar);
        AppBarTitle_ADSS.setText("Student Details ..");

        Intent intent = getIntent();
        if (intent != null) {
            String fname = intent.getStringExtra("fname");
            String surname = intent.getStringExtra("surname");
            String Sid = intent.getStringExtra("Sid");
            String mono = intent.getStringExtra("mono");
            String email = intent.getStringExtra("email");
            String dob = intent.getStringExtra("dob");
            String gen = intent.getStringExtra("gen");
            String clg = intent.getStringExtra("clg");

            //changed all txtview to edittext at 9/8/23 10:30

            EditText fnameTextView = findViewById(R.id.AD_Fname);
            EditText surnameTextView = findViewById(R.id.AD_Surname);
            EditText SidTextView = findViewById(R.id.AD_Sid);
            EditText MonoTextView = findViewById(R.id.AD_Mono);
            EditText EmailTextView = findViewById(R.id.AD_Email);
            EditText DobTextView = findViewById(R.id.AD_Dob);
            EditText GenTextView = findViewById(R.id.AD_Gender);
            EditText ClgTextView = findViewById(R.id.AD_College);

            fnameTextView.setText(fname);
            surnameTextView.setText(surname);
            SidTextView.setText(Sid);
            MonoTextView.setText(mono);
            EmailTextView.setText(email);
            DobTextView.setText(dob);
            GenTextView.setText(gen);
            ClgTextView.setText(clg);

            del = findViewById(R.id.btn_delete);
            update = findViewById(R.id.btn_update);

            update.setOnClickListener(view -> {
                String a = fnameTextView.getText().toString();
                String b = surnameTextView.getText().toString();
                String c = SidTextView.getText().toString();
                String d = MonoTextView.getText().toString();
                String e = EmailTextView.getText().toString();
                String f = DobTextView.getText().toString();
                String g = GenTextView.getText().toString();
                String h = ClgTextView.getText().toString();

                update_data(a, b, c, d, e, f, g, h);
            });

            del.setOnClickListener(view ->
                    db.collection("Users").document(EmailTextView.getText().toString())
                            .delete()
                            .addOnCompleteListener(task -> {
                                Toast.makeText(_Admin_Details_StelectedStudent.this, "deleted ", Toast.LENGTH_SHORT).show();
                                finish();
                            }).addOnFailureListener(e ->
                                    Toast.makeText(_Admin_Details_StelectedStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show()));
        }
    }

    private void update_data(String a, String b, String c, String d, String e, String f, String g, String h) {
        FirebaseFirestore.setLoggingEnabled(true);

        Log.d("c", c);
        db.collection("Users").document(e).update(
                        "Student_First_Name", a,
                        "Surname", b,
                        "Student_Id", c,
                        "Mobile_No", d,
                        "Email_Id", e,
                        "Date_Of_Birth", f,
                        "College", g,
                        "Gender", h)
                .addOnCompleteListener(task -> {
                    Toast.makeText(_Admin_Details_StelectedStudent.this, "Updated Successfully..", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e1 -> Toast.makeText(_Admin_Details_StelectedStudent.this, e1.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
