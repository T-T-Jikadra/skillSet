package com.example.back_benchers._Student_Profile_Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.back_benchers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Educational_Fragment extends Fragment {
    // Key for the shared preferences
    private static final String PREF_KEY_EDUCATIONAL_DETAILS_ENTERED = "Educational_Details_Entered";
    // Input fields
    TextView txt_Clg_Educate;
    TextView txt_Course_Educate;
    TextView txt_Sid_Educate;
    EditText edInSem_Educate;
    EditText edInDiv_Educate;
    EditText edInRno_Educate;
    // Submit button
    Button btn_DataSubmit_Education;
    // Shared preferences
    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_educational, container, false);

        // Initialize FirebaseAuth and get the current user
        FirebaseAuth fAuth_Education = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth_Education.getCurrentUser();

        // Initialize input fields and the submit button
        txt_Clg_Educate = view.findViewById(R.id.edClg);
        txt_Course_Educate = view.findViewById(R.id.edCourse);
        edInSem_Educate = view.findViewById(R.id.edSem);
        txt_Sid_Educate = view.findViewById(R.id.edSid);
        edInDiv_Educate = view.findViewById(R.id.edDiv);
        edInRno_Educate = view.findViewById(R.id.edRno);
        btn_DataSubmit_Education = view.findViewById(R.id.EducateData_Submit);

        // Initialize shared preferences
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0);

        // Get the user's ID and reference to the document
        String uID = Objects.requireNonNull(firebaseUser).getEmail();
        DocumentReference userRef = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(uID));

        // Fetch user details from Firestore
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {

                // Retrieve user profile data from the document
                String Edu_Details_Course = documentSnapshot.getString("Course_Name");
                String Edu_Details_Clg = documentSnapshot.getString("Department");
                String Edu_Details_Sid = documentSnapshot.getString("Student_Id");

                // Find TextViews in the layout to display profile data
                TextView txteducate_clg = view.findViewById(R.id.edClg);
                TextView txteducate_course = view.findViewById(R.id.edCourse);
                TextView txteducate_Sid = view.findViewById(R.id.edSid);

                // Set profile data to the TextViews
                txteducate_clg.setText(Edu_Details_Course);
                txteducate_course.setText(Edu_Details_Clg);
                txteducate_Sid.setText(Edu_Details_Sid);

                // Set values in input fields from Firestore
                setEditTextValue(edInSem_Educate, "Stud_EducationDetails_Sem", documentSnapshot);
                setEditTextValue(edInDiv_Educate, "Stud_EducationDetails_Div", documentSnapshot);
                setEditTextValue(edInRno_Educate, "Stud_EducationDetails_Rno", documentSnapshot);

                // Disable submit button if values are found
                boolean hasValues = checkValuesExist(edInSem_Educate, edInDiv_Educate, edInRno_Educate);

                if (hasValues) {
                    btn_DataSubmit_Education.setEnabled(false);
                    disableInputFields();
                } else {
                    btn_DataSubmit_Education.setEnabled(true); // Enable the button if no values are found
                    enableInputFields(); // Enable the input fields
                }

            } else {
                Toast.makeText(requireContext(), "User Details Not Found ..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(requireContext(), "Failed To Fetch User Details ..", Toast.LENGTH_SHORT).show());

        // Handle submit button click
        btn_DataSubmit_Education.setOnClickListener(v -> onSubmitButtonClicked());

        return view;
    }

    // Check if values exist in the input fields
    private boolean checkValuesExist(EditText... editTexts) {
        for (EditText editText : editTexts) {
            String text = Objects.requireNonNull(editText.getText()).toString().trim();
            if (!text.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Set the value of an input field from Firestore
    private void setEditTextValue(EditText editText, String fieldName, DocumentSnapshot documentSnapshot) {
        String fetchedDetails = documentSnapshot.getString(fieldName);
        editText.setText(fetchedDetails != null ? fetchedDetails : "");
        editText.setEnabled(false); // Disable input field if value is present
    }

    // Handle the click event of the submit button
    private void onSubmitButtonClicked() {
        // Validate the data
        boolean isEducateDataValid = validateEducationData();
        if (isEducateDataValid) {
            String[] fieldNames = {
                    "Stud_EducationDetails_Sem",
                    "Stud_EducationDetails_Div",
                    "Stud_EducationDetails_Rno"
            };

            // Get the current user and reference to the document
            FirebaseAuth fAuth_EducationSubmit = FirebaseAuth.getInstance();
            FirebaseUser fUser_EducationSubmit = fAuth_EducationSubmit.getCurrentUser();
            String uID_Educational = Objects.requireNonNull(fUser_EducationSubmit).getEmail();

            DocumentReference Ref_EducationDataRef = FirebaseFirestore
                    .getInstance()
                    .collection("Users")
                    .document(Objects.requireNonNull(uID_Educational));

            // Create a map to store educational data
            Map<String, Object> educationalData = new HashMap<>();

            // Add educational data to the map
            addEducationalData(educationalData, edInSem_Educate, fieldNames[0]);
            addEducationalData(educationalData, edInDiv_Educate, fieldNames[1]);
            addEducationalData(educationalData, edInRno_Educate, fieldNames[2]);

            if (!educationalData.isEmpty()) {
                // Update the document with the educational data
                Ref_EducationDataRef.update(educationalData).addOnSuccessListener(aVoid -> {
                    sharedPreferences.edit().putBoolean(PREF_KEY_EDUCATIONAL_DETAILS_ENTERED, true).apply();
                    Toast.makeText(requireContext(),
                                    "Educational Details Submitted Successfully !",
                                    Toast.LENGTH_SHORT)
                            .show();
                    disableInputFields();
                }).addOnFailureListener(e ->
                        Toast.makeText(requireContext(),
                                        "Failed To Submit Educational Details",
                                        Toast.LENGTH_SHORT)
                                .show());
            } else {
                Toast.makeText(requireContext(),
                                "Please Enter at Least One Educational Detail ..",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    // Validate the data before submitting
    boolean validateEducationData() {
        if (Objects.requireNonNull(txt_Course_Educate.getText()).toString().trim().isEmpty()) {
            txt_Course_Educate.setError("Enter Course Details, It Can't be null ..");
            txt_Course_Educate.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edInSem_Educate.getText()).toString().trim().isEmpty()) {
            edInSem_Educate.setError("Enter Semester Details ..");
            edInSem_Educate.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(txt_Sid_Educate.getText()).toString().trim().isEmpty()) {
            txt_Sid_Educate.setError("Enter SID Details ..");
            txt_Sid_Educate.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edInDiv_Educate.getText()).toString().trim().isEmpty()) {
            edInDiv_Educate.setError("Enter Division Details ..");
            edInDiv_Educate.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(edInRno_Educate.getText()).toString().trim().isEmpty()) {
            edInRno_Educate.setError("Enter Roll Number Details ..");
            edInRno_Educate.requestFocus();
            return false;
        }
        return true;
    }

    // Add educational data to the map
    private void addEducationalData(Map<String, Object> educationalData, EditText editText, String fieldName) {
        String enteredDetails = Objects.requireNonNull(editText.getText()).toString();
        if (!enteredDetails.isEmpty()) {
            educationalData.put(fieldName, enteredDetails);
        }
    }

    // Enable input fields
    private void enableInputFields() {
        txt_Course_Educate.setEnabled(true);
        edInSem_Educate.setEnabled(true);
        txt_Sid_Educate.setEnabled(true);
        edInDiv_Educate.setEnabled(true);
        edInRno_Educate.setEnabled(true);
    }

    // Disable input fields
    private void disableInputFields() {
        txt_Course_Educate.setEnabled(false);
        edInSem_Educate.setEnabled(false);
        txt_Sid_Educate.setEnabled(false);
        edInDiv_Educate.setEnabled(false);
        edInRno_Educate.setEnabled(false);
    }
}

