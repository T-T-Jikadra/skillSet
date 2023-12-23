package com.example.back_benchers._Student_Profile_Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.back_benchers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Personal_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase components
        FirebaseAuth fAuth_HomeFr = FirebaseAuth.getInstance();
        FirebaseUser fUser_HomeFr = fAuth_HomeFr.getCurrentUser();
        FirebaseFirestore fStore_HomeFr = FirebaseFirestore.getInstance();

        if (fUser_HomeFr == null) {
            // Display a message if the user is not logged in
            Toast.makeText(requireContext(),
                            "Not A User ..",
                            Toast.LENGTH_SHORT)
                    .show();
        } else {
            String uID_HomeFr = fUser_HomeFr.getEmail();

            // Get a reference to the user's profile data document
            DocumentReference ProfileDataRef = fStore_HomeFr.collection("Users")
                    .document(Objects.requireNonNull(uID_HomeFr));
            ProfileDataRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Retrieve user profile data from the document
                        String str_Fname_Home = snapshot.getString("Student_First_Name");
                        String str_Surname_Home = snapshot.getString("Surname");
                        String str_Mobile_Home = snapshot.getString("Mobile_No");
                        String str_Email_Home = snapshot.getString("Email_Id");
                        String str_Sid_Home = snapshot.getString("Student_Id");
                        String str_Bdate_Home = snapshot.getString("Date_Of_Birth");
                        String str_Gender_Home = snapshot.getString("Gender");

                        // Find TextViews in the layout to display profile data
                        TextView txt_profile_fname_Home = view.findViewById(R.id.edFname);
                        TextView txt_profile_Surname_Home = view.findViewById(R.id.edsurname);
                        TextView txt_profile_Sid_Home = view.findViewById(R.id.edsid);
                        TextView txt_profile_Mono_Home = view.findViewById(R.id.edmono);
                        TextView txt_profile_Email_Home = view.findViewById(R.id.edmail);
                        TextView txt_profile_Bdate_Home = view.findViewById(R.id.edbdate);
                        TextView txt_profile_Gen_Home = view.findViewById(R.id.edgen);

                        // Set profile data to the TextViews
                        txt_profile_fname_Home.setText(str_Fname_Home);
                        txt_profile_Surname_Home.setText(str_Surname_Home);
                        txt_profile_Sid_Home.setText(str_Sid_Home);
                        txt_profile_Mono_Home.setText(str_Mobile_Home);
                        txt_profile_Email_Home.setText(str_Email_Home);
                        txt_profile_Bdate_Home.setText(str_Bdate_Home);
                        txt_profile_Gen_Home.setText(str_Gender_Home);
                    }
                }
            });
        }
    }
}
