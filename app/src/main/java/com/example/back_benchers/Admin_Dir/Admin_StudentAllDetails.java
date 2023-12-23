package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin_StudentAllDetails extends AppCompatActivity {
    FirebaseFirestore firestore_StudDetails;
    ImageButton backBtn_AdminStudAllDetail;
    Button btn_UpdateData;
    Button btn_DelData;
    TextView AppBarTitle_ASAD;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_all_details);

        // Initialize Firestore
        firestore_StudDetails = FirebaseFirestore.getInstance();

        // Initialize UI elements
        backBtn_AdminStudAllDetail = findViewById(R.id.backBtn_1);
        backBtn_AdminStudAllDetail.setOnClickListener(view -> onBackPressed());

        AppBarTitle_ASAD = findViewById(R.id.txtId_appBar);
        AppBarTitle_ASAD.setText("Student Details ..");

        // Get details from the intent
        Intent intentDetails = getIntent();
        if (intentDetails != null) {
            // Retrieve data from the intent
            String strFname = intentDetails.getStringExtra("fname");
            String strSurname = intentDetails.getStringExtra("surname");
            String strSid = intentDetails.getStringExtra("Sid");
            String strMono = intentDetails.getStringExtra("mono");
            String strEmail = intentDetails.getStringExtra("email");
            String strDob = intentDetails.getStringExtra("dob");
            String strGen = intentDetails.getStringExtra("gen");
            String strClg = intentDetails.getStringExtra("clg");
            String strCourse = intentDetails.getStringExtra("course");
            String strFees = intentDetails.getStringExtra("fees");
            String strCreatedOn = intentDetails.getStringExtra("createdOn");

            // Initialize TextViews with retrieved data
            TextView fnameTxt = findViewById(R.id.text1_fname);
            TextView surnameTxt = findViewById(R.id.text1_surname);
            TextView SidTxt = findViewById(R.id.text1_sid);
            TextView MonoTxt = findViewById(R.id.text1_mobile);
            TextView EmailTxt = findViewById(R.id.text1_email);
            TextView DobTxt = findViewById(R.id.text1_dob);
            TextView GenTxt = findViewById(R.id.text1_gender);
            TextView ClgTxt = findViewById(R.id.text1_college);
            TextView CourseTxt = findViewById(R.id.text1_courseName);
            TextView FeesTxt = findViewById(R.id.text1_fees);
            TextView CreatedOnTxt = findViewById(R.id.text1_UCdtae);

            fnameTxt.setText(strFname);
            surnameTxt.setText(strSurname);
            SidTxt.setText(strSid);
            MonoTxt.setText(strMono);
            EmailTxt.setText(strEmail);
            DobTxt.setText(strDob);
            GenTxt.setText(strGen);
            ClgTxt.setText(strClg);
            CourseTxt.setText(strCourse);
            FeesTxt.setText(strFees);
            CreatedOnTxt.setText(strCreatedOn);

            // Set up button click listeners
            btn_DelData = findViewById(R.id.btn_DeleteStudData);
            btn_UpdateData = findViewById(R.id.btn_UpdateStudData);

            // Set up update button click listener
            btn_UpdateData.setOnClickListener(view -> {
                // Prepare data for update
                String a = fnameTxt.getText().toString();
                String b = surnameTxt.getText().toString();
                String c = SidTxt.getText().toString();
                String d = MonoTxt.getText().toString();
                String e = EmailTxt.getText().toString();
                String f = DobTxt.getText().toString();
                String g = GenTxt.getText().toString();
                String h = ClgTxt.getText().toString();
                String i = CourseTxt.getText().toString();
                String j = FeesTxt.getText().toString();
                String k = CreatedOnTxt.getText().toString();

                // Create intent for update activity
                Intent UpIntent = new Intent(this, Admin_updateStudentData.class);
                UpIntent.putExtra("Detailsfname", a);
                UpIntent.putExtra("Detailssurname", b);
                UpIntent.putExtra("DetailsSid", c);
                UpIntent.putExtra("Detailsmono", d);
                UpIntent.putExtra("Detailsemail", e);
                UpIntent.putExtra("Detailsdob", f);
                UpIntent.putExtra("Detailsgen", g);
                UpIntent.putExtra("Detailsclg", h);
                UpIntent.putExtra("Detailscourse", i);
                UpIntent.putExtra("Detailsfees", j);
                UpIntent.putExtra("Detailscreatedon", k);

                startActivity(UpIntent);
                finish();
            });

            // Set up delete button click listener
            btn_DelData.setOnClickListener(view -> {
                // Create a confirmation dialog
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.costom_alert_dialog_delete);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.file_round_corner));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                Button adb1 = dialog.findViewById(R.id.albtn1);
                Button adb2 = dialog.findViewById(R.id.albtn2);

                adb1.setOnClickListener(view1 -> {
                    // Delete user data from Firestore
                    firestore_StudDetails.collection("Users").document(EmailTxt.getText().toString())
                            .delete()
                            .addOnCompleteListener(task -> {
                                Toast.makeText(getApplicationContext(),
                                                "User Deleted Successfully .. ",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }).addOnFailureListener(e ->
                                    Toast.makeText(getApplicationContext(),
                                                    e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show());
                });

                adb2.setOnClickListener(view1 -> dialog.cancel());
                dialog.show();
            });
        }
    }
}
