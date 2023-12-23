package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin_updateStudentData extends AppCompatActivity {
    FirebaseFirestore firestore_StudUpdate;
    ImageButton backBtn_AdminStudUpdate;
    Button btn_UpdateStudData;
    TextView AppBarTitle_AUSD;
    EditText Edfname_StudUpdate;
    EditText Edsurname_StudUpdate;
    TextView TxtSid_StudUpdate;
    TextView TxtMono_StudUpdate;
    TextView TxtEmail_StudUpdate;
    EditText EdDob_StudUpdate;
    EditText EdGen_StudUpdate;
    TextView TxtClg_StudUpdate;
    TextView TxtCourse_StudUpdate;
    TextView TxtFees_StudUpdate;
    TextView TxtCreated_StudUpdate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_student_data);

        // Initialize Firestore instance
        firestore_StudUpdate = FirebaseFirestore.getInstance();

        // Initialize Views
        backBtn_AdminStudUpdate = findViewById(R.id.backBtn_1);
        backBtn_AdminStudUpdate.setOnClickListener(view -> onBackPressed());

        AppBarTitle_AUSD = findViewById(R.id.txtId_appBar);
        AppBarTitle_AUSD.setText("Student Update Details ..");

        // Retrieve intent data
        Intent intent1 = getIntent();
        if (intent1 != null) {
            String strFname = intent1.getStringExtra("Detailsfname");
            String strSurname = intent1.getStringExtra("Detailssurname");
            String strSid = intent1.getStringExtra("DetailsSid");
            String strMono = intent1.getStringExtra("Detailsmono");
            String strEmail = intent1.getStringExtra("Detailsemail");
            String strDob = intent1.getStringExtra("Detailsdob");
            String strGen = intent1.getStringExtra("Detailsgen");
            String strClg = intent1.getStringExtra("Detailsclg");
            String strCourse = intent1.getStringExtra("Detailscourse");
            String strFees = intent1.getStringExtra("Detailsfees");
            String strCreatedOn = intent1.getStringExtra("Detailscreatedon");

            // Initialize EditTexts for updating data
            Edfname_StudUpdate = findViewById(R.id.Update_fname);
            Edsurname_StudUpdate = findViewById(R.id.Update_surname);
            TxtSid_StudUpdate = findViewById(R.id.Update_sid);
            TxtMono_StudUpdate = findViewById(R.id.Update_mobile);
            TxtEmail_StudUpdate = findViewById(R.id.Update_email);
            EdDob_StudUpdate = findViewById(R.id.Update_dob);
            EdGen_StudUpdate = findViewById(R.id.Update_gender);
            TxtClg_StudUpdate = findViewById(R.id.Update_college);
            TxtCourse_StudUpdate = findViewById(R.id.Update_course);
            TxtFees_StudUpdate = findViewById(R.id.Update_fees);
            TxtCreated_StudUpdate = findViewById(R.id.Update_UCdtae);

            // Set retrieved data to EditTexts
            Edfname_StudUpdate.setText(strFname);
            Edsurname_StudUpdate.setText(strSurname);
            TxtSid_StudUpdate.setText(strSid);
            TxtMono_StudUpdate.setText(strMono);
            TxtEmail_StudUpdate.setText(strEmail);
            EdDob_StudUpdate.setText(strDob);
            EdGen_StudUpdate.setText(strGen);
            TxtClg_StudUpdate.setText(strClg);
            TxtCourse_StudUpdate.setText(strCourse);
            TxtFees_StudUpdate.setText(strFees);
            TxtCreated_StudUpdate.setText(strCreatedOn);

            // Initialize and configure the Update button
            btn_UpdateStudData = findViewById(R.id.btnId_UpdateData);
            btn_UpdateStudData.setOnClickListener(view -> {
                String updatedFname = Edfname_StudUpdate.getText().toString();
                String updatedSurname = Edsurname_StudUpdate.getText().toString();
                String updatedSid = TxtSid_StudUpdate.getText().toString();
                String updatedMono = TxtMono_StudUpdate.getText().toString();
                String updatedEmail = TxtEmail_StudUpdate.getText().toString();
                String updatedDob = EdDob_StudUpdate.getText().toString();
                String updatedGen = EdGen_StudUpdate.getText().toString();
                String updatedClg = TxtClg_StudUpdate.getText().toString();

                // Finish the activity and update the data
                finish();
                updateData(updatedFname, updatedSurname, updatedSid, updatedMono, updatedEmail, updatedDob, updatedGen, updatedClg);
            });
        }

        TxtSid_StudUpdate.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(),
                                "SID Can't Be Updated .. ", Toast.LENGTH_SHORT)
                        .show());
        TxtMono_StudUpdate.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(),
                                "Mobile Number Can't Be Updated .. ", Toast.LENGTH_SHORT)
                        .show());
        TxtEmail_StudUpdate.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(),
                                "Email Address Can't Be Updated .. ", Toast.LENGTH_SHORT)
                        .show());
        TxtClg_StudUpdate.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(),
                                "Department Name Can't Be Updated .. ", Toast.LENGTH_SHORT)
                        .show());
        TxtCourse_StudUpdate.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(),
                                "Course Name Can't Be Updated .. ", Toast.LENGTH_SHORT)
                        .show());
    }

    private void updateData(String a, String b, String c, String d, String e, String f, String g, String h) {
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Update student data in Firestore collection
        firestore_StudUpdate.collection("Users").document(e).update(
                        "Student_First_Name", a,
                        "Surname", b,
                        "Student_Id", c,
                        "Mobile_No", d,
                        "Email_Id", e,
                        "Date_Of_Birth", f,
                        "College", g,
                        "Gender", h)
                .addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(),
                                    "Student's Data Updated Successfully ..",
                                    Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }).addOnFailureListener(e1 ->
                        Toast.makeText(getApplicationContext(),
                                        e1.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show());
    }
}
