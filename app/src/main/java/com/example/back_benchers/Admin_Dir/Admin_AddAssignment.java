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

public class Admin_AddAssignment extends AppCompatActivity {
    ImageButton backBtn_AdminAss;
    Button btn_UploadFile;
    TextInputEditText edin_FileName;
    ListView listview_showAssignmentToAdmin;
    List<Assignment_Item> uploads_Assign_list;
    StorageReference storeRef_AdminAss;
    DatabaseReference databaseRef_AdminAss;
    TextView AppBarTitle_AAAssign;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_assignment);

        // Initialize UI elements
        backBtn_AdminAss = findViewById(R.id.backBtn_1);
        AppBarTitle_AAAssign = findViewById(R.id.txtId_appBar);

        edin_FileName = findViewById(R.id.txt_inputFileName);
        btn_UploadFile = findViewById(R.id.uploadAssignBTN);
        listview_showAssignmentToAdmin = findViewById(R.id.lvId_AdminAssign);

        // Set a click listener for the back button to navigate back
        backBtn_AdminAss.setOnClickListener(view -> onBackPressed());

        // Set the title for the app bar
        AppBarTitle_AAAssign.setText("Assignments ..");

        // Initialize Firebase storage and database references
        storeRef_AdminAss = FirebaseStorage.getInstance().getReference();
        databaseRef_AdminAss = FirebaseDatabase.getInstance().getReference("Assignments");

        // Load and display existing assignment files
        AdminAssign_ViewAllFiles();

        // Initialize the list to store assignment uploads
        uploads_Assign_list = new ArrayList<>();

        // Set click listener for the upload button
        btn_UploadFile.setOnClickListener(view -> {
            if (Objects.requireNonNull(edin_FileName.getText()).toString().isEmpty()) {
                edin_FileName.setError("File Name Required ..");
                edin_FileName.requestFocus();
            } else {
                select_AdAss_FilesFromLocal();
            }
        });

        // Set click listener for items in the list view
        listview_showAssignmentToAdmin.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    Assignment_Item pdfUpload_Admin = uploads_Assign_list.get(i);
                    String pdfUrl = pdfUpload_Admin.getUrl();

                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
                        Intent intentAdminAss = new Intent(Intent.ACTION_VIEW);
                        intentAdminAss.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                        intentAdminAss.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intentAdminAss);
                    } else {
                        // Handle the case where the PDF URL is not available
                        Toast.makeText(getApplicationContext(),
                                        "PDF URL is not available",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    // Load and display existing assignment files
    private void AdminAssign_ViewAllFiles() {
        databaseRef_AdminAss = FirebaseDatabase.getInstance().getReference("Assignments");
        databaseRef_AdminAss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploads_Assign_list.clear(); // Clear the list before adding new data
                for (DataSnapshot poatsnapshot : snapshot.getChildren()) {
                    Assignment_Item assignment_item = poatsnapshot.getValue(Assignment_Item.class);
                    uploads_Assign_list.add(0, assignment_item);
                }

                // Create an array of names for assignment items
                String[] Uploads_Array = new String[uploads_Assign_list.size()];
                for (int i = 0; i < Uploads_Array.length; i++) {
                    Uploads_Array[i] = uploads_Assign_list.get(i).getName();
                }

                // Create an adapter and set it to the list view
                Assignment_ItemAdapter adapterAdmin = new Assignment_ItemAdapter(
                        getApplicationContext(),
                        uploads_Assign_list);

                listview_showAssignmentToAdmin.setAdapter(adapterAdmin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    // Open file chooser to select assignment files from local storage
    private void select_AdAss_FilesFromLocal() {
        Intent intent_selectFiles = new Intent();
        intent_selectFiles.setType("application/pdf");
        intent_selectFiles.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent_selectFiles, "Select Pdf Files : "), 1);
    }

    // Handle the result of the file selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Upload_AssignPDFIntoDatabase(data.getData());
        }
    }

    // Upload selected assignment PDF file to the database
    private void Upload_AssignPDFIntoDatabase(Uri data) {
        final ProgressDialog progressDialog_upload = new ProgressDialog(this);
        progressDialog_upload.setTitle("Uploading Assignments ..");
        progressDialog_upload.show();

        StorageReference reference = storeRef_AdminAss.child("Assignments/" + Objects.requireNonNull(edin_FileName.getText()) + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(
                        taskSnapshot -> {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri url = uriTask.getResult();

                            Assignment_Item Ass_Item = new Assignment_Item(Objects.requireNonNull(edin_FileName.getText()).toString(), url.toString());
                            databaseRef_AdminAss.child(Objects.requireNonNull(databaseRef_AdminAss.push().getKey())).setValue(Ass_Item);
                            Toast.makeText(getApplicationContext(),
                                            "File Uploaded Successfully ..",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressDialog_upload.dismiss();
                        })
                .addOnProgressListener(
                        snapshot -> {
                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            progressDialog_upload.setMessage("File Uploaded : " + (int) progress + " %");
                        });
    }
}