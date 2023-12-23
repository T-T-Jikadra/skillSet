package com.example.back_benchers.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.back_benchers.R;

import java.util.List;

public class Assignment_ItemAdapter extends ArrayAdapter<Assignment_Item> {

    // Constructor for the adapter
    public Assignment_ItemAdapter(Context context, List<Assignment_Item> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        // Inflate the layout if the view is null
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_assignment, parent, false);
        }

        // Get the Assignment_Item at the current position
        Assignment_Item assignmentItem = getItem(position);

        // Find the TextView to display the assignment name
        TextView assignmentNameTextView = view.findViewById(R.id.text_assignment_name);

        // Set the assignment name to the TextView
        if (assignmentItem != null) {
            assignmentNameTextView.setText(assignmentItem.getName());
        }

        // Return the prepared view for the ListView/GridView item
        return view;
    }
}
