package com.example.back_benchers.Adpters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.back_benchers.Admin_Dir.Admin_StudentAllDetails;
import com.example.back_benchers.R;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// Adapter for displaying student items in a RecyclerView
public class Stud_ItemAdapter extends RecyclerView.Adapter<Stud_ItemAdapter.ViewHolder> {

    private List<Stud_Item> itemList; // List of student items

    // Constructor to initialize the adapter with a list of student items
    public Stud_ItemAdapter(List<Stud_Item> itemList) {
        this.itemList = itemList;
    }

    // Create a ViewHolder for inflating the layout for each item in the RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stud_recycler, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to the ViewHolder and set click listeners
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stud_Item item = itemList.get(position);
        holder.bind(item);

        // Set a click listener to open a detailed activity for the selected student
        holder.itemView.setOnClickListener(view -> {
            Intent intentAdapter = new Intent(view.getContext(), Admin_StudentAllDetails.class);
            // Pass individual fields using putExtra
            intentAdapter.putExtra("fname", item.getStudent_First_Name());
            intentAdapter.putExtra("surname", item.getSurname());
            intentAdapter.putExtra("Sid", item.getStudent_Id());
            intentAdapter.putExtra("mono", item.getMobile_No());
            intentAdapter.putExtra("email", item.getEmail_Id());
            intentAdapter.putExtra("dob", item.getDate_Of_Birth());
            intentAdapter.putExtra("gen", item.getGender());
            intentAdapter.putExtra("clg", item.getDepartment());
            intentAdapter.putExtra("course", item.getCourse_Name());
            intentAdapter.putExtra("createdOn", item.getUser_Created_On().toDate().toString());
            intentAdapter.putExtra("fees", item.getIsFees());
            view.getContext().startActivity(intentAdapter);
        });

        Log.d("On Bind", "Binded : " + item.getDate_Of_Birth());
    }

    // Get the total number of items in the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Method to update the adapter data
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Stud_Item> newList) {
        itemList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    // ViewHolder class for representing each item's view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView CardFname;
        TextView CardSurname;
        TextView CardSid;
        TextView CardClg;
        TextView CardDOB;
        TextView CardEmail;
        TextView CardGender;
        TextView CardMoNo;
        TextView CardcourseName;
        TextView feesStatus;
        TextView CardcreatedOnTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the layout
            CardFname = itemView.findViewById(R.id.text1_fname);
            CardSurname = itemView.findViewById(R.id.text1_surname);
            CardSid = itemView.findViewById(R.id.text1_sid);
            CardMoNo = itemView.findViewById(R.id.text1_mobile);
            CardEmail = itemView.findViewById(R.id.text1_email);
            CardDOB = itemView.findViewById(R.id.text1_dob);
            CardGender = itemView.findViewById(R.id.text1_gender);
            CardClg = itemView.findViewById(R.id.text1_college);
            CardcourseName = itemView.findViewById(R.id.text1_courseName);
            feesStatus = itemView.findViewById(R.id.text1_fees);
            CardcreatedOnTextView = itemView.findViewById(R.id.text1_UCdtae);
        }

        // Bind data to the ViewHolder's views
        @SuppressLint("SetTextI18n")
        public void bind(Stud_Item item) {
            CardFname.setText("Student Name : " + item.getStudent_First_Name() + " " + item.getSurname());
            //CardSurname.setText("Surname : "+item.getSurname());
            CardSid.setText("Student Id : " + item.getStudent_Id());
            CardcreatedOnTextView.setText(timeStampToStr(item.getUser_Created_On()));
            Log.d("bind", "Adapter Fields Set ..");
        }

        // Convert a timestamp to a formatted string
        @SuppressLint("SimpleDateFormat")
        private String timeStampToStr(Timestamp user_created_on) {
            return new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(user_created_on.toDate());
        }
    }
}