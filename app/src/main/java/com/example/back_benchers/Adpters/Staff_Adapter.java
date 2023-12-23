package com.example.back_benchers.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.back_benchers.R;

import java.util.ArrayList;

public class Staff_Adapter extends ArrayAdapter<String> {

    public Staff_Adapter(Context context, ArrayList<String> data) {
        super(context, R.layout.layout_staff_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_staff_item, parent, false);
        }

        String currentItem = getItem(position);

        TextView txtListItem = listItemView.findViewById(R.id.txtListItem);
        txtListItem.setText(currentItem);

//        listItemView.setOnClickListener(view -> {
//            Toast.makeText(this.getContext(), "Faculty Name : "+currentItem, Toast.LENGTH_SHORT).show();
//        });

        return listItemView;
    }
}
