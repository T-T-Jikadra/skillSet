package com.example.back_benchers.Admin_Dir;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.back_benchers.Adpters.Assignment_Item;
import com.example.back_benchers.Adpters.Assignment_ItemAdapter;
import com.example.back_benchers.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_Add_Study_Material extends AppCompatActivity {
    ImageButton backBtn_AddStudyMat;
    Button btn_UploadStudyMat;
    TextInputEditText edin_StudyMatFileName;
    ListView listview_showStudyMatToAdmin;
    List<Assignment_Item> uploads_AdStudyMat_list;
    StorageReference storeRef_AdminStudyMat;
    DatabaseReference databaseRef_AdminStudyMat;
    TextView AppBarTitle_AASM;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_study_material);

        // Set a click listener for the back button to navigate back
        backBtn_AddStudyMat = findViewById(R.id.backBtn_1);
        backBtn_AddStudyMat.setOnClickListener(view -> onBackPressed());

        // Set the title for the app bar
        AppBarTitle_AASM = findViewById(R.id.txtId_appBar);
        AppBarTitle_AASM.setText("Study Materials ..");

        // Initialize UI elements

        edin_StudyMatFileName = findViewById(R.id.txt_inputStudyMaterialName);
        btn_UploadStudyMat = findViewById(R.id.upload_StudyMatBTN);
        listview_showStudyMatToAdmin = findViewById(R.id.lvId_AdminStudyMat);

        // Initialize Firebase storage and database references
        storeRef_AdminStudyMat = FirebaseStorage.getInstance().getReference();
        databaseRef_AdminStudyMat = FirebaseDatabase.getInstance().getReference("Study Materials");

        // Set click listener for upload button
        btn_UploadStudyMat.setOnClickListener(view -> {
            if (Objects.requireNonNull(edin_StudyMatFileName.getText()).toString().isEmpty()) {
                edin_StudyMatFileName.setError("Material File Name Required ..");
                edin_StudyMatFileName.requestFocus();
            } else {
                select_SM_FilesFromLocal();
            }
        });

        // Set click listener for items in the list view
        listview_showStudyMatToAdmin.setOnItemClickListener((adapterView, view, i, l) -> {
            Assignment_Item StudyMatUpload_Admin = uploads_AdStudyMat_list.get(i);
            String pdfUrl = StudyMatUpload_Admin.getUrl();

            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                Intent intent_AdminStudyMat = new Intent(Intent.ACTION_VIEW);
                intent_AdminStudyMat.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                intent_AdminStudyMat.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent_AdminStudyMat);
            } else {
                // Handle the case where the PDF URL is not available
                Toast.makeText(getApplicationContext(), "PDF URL is not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the list to store study material uploads
        uploads_AdStudyMat_list = new ArrayList<>();

        // Load and display existing study material files
        AdminStudyMat_ViewAllFiles();
    }

    // Load and display existing study material files
    private void AdminStudyMat_ViewAllFiles() {
        // Set up database reference
        databaseRef_AdminStudyMat = FirebaseDatabase.getInstance().getReference("Study Materials");
        databaseRef_AdminStudyMat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploads_AdStudyMat_list.clear(); // Clear the list before adding new data
                for (DataSnapshot ASMsnapshot : snapshot.getChildren()) {
                    Assignment_Item AdStudyMat_item = ASMsnapshot.getValue(Assignment_Item.class);
                    uploads_AdStudyMat_list.add(0, AdStudyMat_item);
                }

                // Create an array of names for assignment items
                String[] UploadSM_Array = new String[uploads_AdStudyMat_list.size()];
                for (int i = 0; i < UploadSM_Array.length; i++) {
                    UploadSM_Array[i] = uploads_AdStudyMat_list.get(i).getName();
                }

                // Create an adapter and set it to the list view
                Assignment_ItemAdapter adapterAdmin_StudyMat = new Assignment_ItemAdapter(getApplicationContext(), uploads_AdStudyMat_list);

                listview_showStudyMatToAdmin.setAdapter(adapterAdmin_StudyMat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    // Open file chooser to select study material files from local storage
    private void select_SM_FilesFromLocal() {
        Intent intentSM_selectFiles = new Intent();
        intentSM_selectFiles.setType("application/pdf");
        intentSM_selectFiles.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentSM_selectFiles, "Select Study Material Files : "), 1);
    }

    // Handle the result of the file selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Upload_StudyMatIntoDatabase(data.getData());
        }
    }

    // Upload selected study material file to the database
    private void Upload_StudyMatIntoDatabase(Uri data) {
        final ProgressDialog progressDialog_SM = new ProgressDialog(this);
        progressDialog_SM.setTitle("Uploading Materials ..");
        progressDialog_SM.show();

        StorageReference reference = storeRef_AdminStudyMat.child("Study Materials/" + Objects.requireNonNull(edin_StudyMatFileName.getText()) + ".pdf");
        reference.putFile(data).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete()) ;
            Uri url = uriTask.getResult();
            Assignment_Item Ass_Item = new Assignment_Item(Objects.requireNonNull(edin_StudyMatFileName.getText()).toString(), url.toString());
            databaseRef_AdminStudyMat.child(Objects.requireNonNull(databaseRef_AdminStudyMat.push().getKey())).setValue(Ass_Item);
            Toast.makeText(getApplicationContext(), "Material Uploaded Successfully ..", Toast.LENGTH_SHORT).show();
            progressDialog_SM.dismiss();
        }).addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressDialog_SM.setMessage("Material Uploaded : " + (int) progress + " %");
        });
    }
}