package com.example.back_benchers.Student_Dir;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Student_PayFees extends AppCompatActivity {
    // SharedPreferences key for tracking educational details entry
    private static final String PREF_KEY_FEES_PAID = "educational_details_entered";
    ImageButton backBtn_StudFees;
    TextView AppBarTitle_StudFees;
    TextView txt_feedtotal;
    // UI elements
    TextView txt_paidfeees;
    TextView txt_remfees;
    Button btn_DataSubmit;
    // Firebase Authentication and user references
    FirebaseAuth fAuth_fees;
    FirebaseUser fUser_fees;
    // SharedPreferences for fees payment status
    private SharedPreferences sharedPreferences_Fees;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pay_fees);

        //BAckBtn Functionality..
        backBtn_StudFees = findViewById(R.id.backBtn_1);
        backBtn_StudFees.setOnClickListener(view -> onBackPressed());

        //Title text ..
        AppBarTitle_StudFees = findViewById(R.id.txtId_appBar);
        AppBarTitle_StudFees.setText("Pay Fees ..");

        // Initialize Firebase Authentication
        fAuth_fees = FirebaseAuth.getInstance();
        fUser_fees = fAuth_fees.getCurrentUser();

        // Initialize UI elements from the layout
        txt_feedtotal = findViewById(R.id.feesptotal);
        txt_paidfeees = findViewById(R.id.feespaid);
        txt_remfees = findViewById(R.id.remfees);
        btn_DataSubmit = findViewById(R.id.btnPayFees);

        // Initialize SharedPreferences for tracking fees payment
        sharedPreferences_Fees = getSharedPreferences("MyPrefs_fees", 0);

        // Get the user's email ID from Firebase Authentication
        String uID = Objects.requireNonNull(fUser_fees).getEmail();

        // Get a reference to the user's Firestore document
        DocumentReference userRef = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(uID));

        // Fetch user data from Firestore
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Retrieve fees and course name from Firestore document
                String t1 = documentSnapshot.getString("isFees");
                String courseName = documentSnapshot.getString("Course_Name");

                // Check if fees data exists
                boolean hasValues = checkValueExists(t1);

                if (hasValues) {
                    // Disable payment button and display paid fees data
                    btn_DataSubmit.setEnabled(false);

                    // Update UI based on the course name
                    if (Objects.requireNonNull(courseName).equals("B.C.A.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 15,410");
                        txt_paidfeees.setText("Paid Amount : ₹ 15,410");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : 0");
                    } else if (courseName.equals("B.B.A.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 6,000");
                        txt_paidfeees.setText("Paid Amount : ₹ 6,000");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : 0");
                    } else if (courseName.equals("B.COM")) {
                        txt_feedtotal.setText("Total Amount : ₹ 500");
                        txt_paidfeees.setText("Paid Amount : ₹ 500");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : 0");
                    } else if (courseName.equals("M.B.B.S.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 7,00,00,000");
                        txt_paidfeees.setText("Paid Amount : ₹ 7,00,00,000");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : 0");
                    } else if (courseName.equals("CA")) {
                        txt_feedtotal.setText("Total Amount : ₹ 99,999");
                        txt_paidfeees.setText("Paid Amount : ₹ 99,999");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : 0");
                    }

                } else {
                    // Enable payment button and display remaining fees data
                    btn_DataSubmit.setEnabled(true);

                    // Update UI for unpaid fees
                    if (Objects.requireNonNull(courseName).equals("B.C.A.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 15,410");
                        txt_paidfeees.setText("Paid Amount : 0");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : ₹ 15,410");
                    } else if (courseName.equals("B.B.A.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 6,000");
                        txt_paidfeees.setText("Paid Amount : 0");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : ₹ 6,000");
                    } else if (courseName.equals("B.COM")) {
                        txt_feedtotal.setText("Total Amount : ₹ 500");
                        txt_paidfeees.setText("Paid Amount : 0");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : ₹ 500");
                    } else if (courseName.equals("M.B.B.S.")) {
                        txt_feedtotal.setText("Total Amount : ₹ 7,00,00,000");
                        txt_paidfeees.setText("Paid Amount : 0");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : ₹ 7,00,00,000");
                    } else if (courseName.equals("CA")) {
                        txt_feedtotal.setText("Total Amount : ₹ 99,999");
                        txt_paidfeees.setText("Paid Amount : 0");
                        btn_DataSubmit.setText("Fees Paid ..");
                        txt_remfees.setText("Remaining Amount : ₹ 99,999");
                    }
                }

            } else {
                Toast.makeText(this,
                                "User details not found",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this,
                                "Failed to Fetch Fees Details",
                                Toast.LENGTH_SHORT)
                        .show());

        // Set click listener for the payment button
        btn_DataSubmit.setOnClickListener(v ->
                onPayButtonClicked());
    }

    // Check if the value (fees payment status) exists
    private boolean checkValueExists(String values) {
        return values != null && !values.trim().isEmpty();
    }

    // Handle the payment button click event
    @SuppressLint("SetTextI18n")
    private void onPayButtonClicked() {
        // Get the user's email ID from Firebase Authentication
        String uID = Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                .getEmail();
        DocumentReference userRef = FirebaseFirestore.getInstance().
                collection("Users")
                .document(Objects.requireNonNull(uID));

        // Fetch course name from Firestore and update fees payment status

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String courseName = documentSnapshot.getString("Course_Name");

                // Update fees payment status based on the course name
                if ("B.C.A.".equals(courseName)) {
                    userRef.update("isFees", "paid : ₹ 15,140")
                            .addOnSuccessListener(aVoid -> {
                                sharedPreferences_Fees.edit().putBoolean(PREF_KEY_FEES_PAID, true).apply();
                                Toast.makeText(this,
                                                "Fees Paid Successfully !",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                btn_DataSubmit.setEnabled(false);
                                txt_feedtotal.setText("Total Amount : ₹ 15,410");
                                txt_paidfeees.setText("Paid Amount : ₹ 15,410");
                                btn_DataSubmit.setText("Fees Paid ..");
                                txt_remfees.setText("Remaining Amount : 0");
                            }).addOnFailureListener(e ->
                                    Toast.makeText(this,
                                                    "Failed to Pay Fees ..",
                                                    Toast.LENGTH_SHORT)
                                            .show());
                }
                // Update fees payment status based on the course name
                else if ("B.B.A.".equals(courseName)) {
                    userRef.update("isFees", "paid : ₹ 6,000")
                            .addOnSuccessListener(aVoid -> {
                                sharedPreferences_Fees.edit().putBoolean(PREF_KEY_FEES_PAID, true).apply();
                                Toast.makeText(this,
                                                "Fees Paid Successfully !",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                btn_DataSubmit.setEnabled(false);
                                txt_feedtotal.setText("Total Amount : ₹ 6,000");
                                txt_paidfeees.setText("Paid Amount : ₹ 6,000");
                                btn_DataSubmit.setText("Fees Paid ..");
                                txt_remfees.setText("Remaining Amount : 0");
                            }).addOnFailureListener(e ->
                                    Toast.makeText(this,
                                                    "Failed to Pay Fees ..",
                                                    Toast.LENGTH_SHORT)
                                            .show());
                }
                // Update fees payment status based on the course name
                if ("B.COM".equals(courseName)) {
                    userRef.update("isFees", "paid : ₹ 500")
                            .addOnSuccessListener(aVoid -> {
                                sharedPreferences_Fees.edit().putBoolean(PREF_KEY_FEES_PAID, true).apply();
                                Toast.makeText(this,
                                                "Fees Paid Successfully !",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                btn_DataSubmit.setEnabled(false);
                                txt_feedtotal.setText("Total Amount : ₹ 500");
                                txt_paidfeees.setText("Paid Amount : ₹ 500");
                                btn_DataSubmit.setText("Fees Paid ..");
                                txt_remfees.setText("Remaining Amount : 0");
                            }).addOnFailureListener(e ->
                                    Toast.makeText(this,
                                                    "Failed to Pay Fees",
                                                    Toast.LENGTH_SHORT)
                                            .show());
                }
                // Update fees payment status based on the course name
                if ("M.B.B.S.".equals(courseName)) {
                    userRef.update("isFees", "paid : ₹ 7,00,00,000")
                            .addOnSuccessListener(aVoid -> {
                                sharedPreferences_Fees.edit().putBoolean(PREF_KEY_FEES_PAID, true).apply();
                                Toast.makeText(this,
                                                "Fees Paid Successfully !",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                btn_DataSubmit.setEnabled(false);
                                txt_feedtotal.setText("Total Amount : ₹ 7,00,00,000");
                                txt_paidfeees.setText("Paid Amount : ₹ 7,00,00,000");
                                btn_DataSubmit.setText("Fees Paid ..");
                                txt_remfees.setText("Remaining Amount : 0");
                            }).addOnFailureListener(e ->
                                    Toast.makeText(this,
                                                    "Failed to Pay Fees ..",
                                                    Toast.LENGTH_SHORT)
                                            .show());
                }
                // Update fees payment status based on the course name
                if ("CA".equals(courseName)) {
                    userRef.update("isFees", "paid : ₹ 99,999")
                            .addOnSuccessListener(aVoid -> {
                                sharedPreferences_Fees.edit().putBoolean(PREF_KEY_FEES_PAID, true).apply();
                                Toast.makeText(this,
                                                "Fees Paid Successfully !",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                btn_DataSubmit.setEnabled(false);
                                txt_feedtotal.setText("Total Amount : ₹ 99,999");
                                txt_paidfeees.setText("Paid Amount : ₹ 99,999");
                                btn_DataSubmit.setText("Fees Paid ..");
                                txt_remfees.setText("Remaining Amount : 0");
                            }).addOnFailureListener(e ->
                                    Toast.makeText(this,
                                                    "Failed to Pay Fees ..",
                                                    Toast.LENGTH_SHORT)
                                            .show());
                } else {
                    // Handle other course names here
                }
            } else {
                Toast.makeText(this,
                                "User details not found",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this,
                                "Failed to fetch user details",
                                Toast.LENGTH_SHORT)
                        .show());
    }
}