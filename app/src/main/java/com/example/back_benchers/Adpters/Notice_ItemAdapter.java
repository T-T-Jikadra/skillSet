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

import com.example.back_benchers.R;
import com.example.back_benchers.Student_Dir.Student_ShowNoticeData;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class Notice_ItemAdapter extends RecyclerView.Adapter<Notice_ItemAdapter.ViewHolder> {

    private List<notice_Item> noticeItemList;

    public Notice_ItemAdapter(List<notice_Item> noticeItemList) {
        this.noticeItemList = noticeItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind the data to the ViewHolder
        notice_Item noticeItem = noticeItemList.get(position);
        holder.bind(noticeItem);

        // Set a click listener to open the notice details activity
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Student_ShowNoticeData.class);
            intent.putExtra("noticeTitle", noticeItem.getNotice_Title());
            intent.putExtra("noticeContent", noticeItem.getNotice_Content());
            intent.putExtra("noticeDocId", noticeItem.getTimestamp().toDate().toString());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the RecyclerView
        return noticeItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView contentTextView;
        private TextView ts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views in the ViewHolder
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            ts = itemView.findViewById(R.id.note_timestamp_text_view);
        }

        public void bind(notice_Item noticeItem) {
            // Bind the notice data to the views in the ViewHolder
            titleTextView.setText(noticeItem.getNotice_Title());
            contentTextView.setText(noticeItem.getNotice_Content());
            ts.setText(timeToStr(noticeItem.getTimestamp()));
            Log.d("Debug", "Binding: " + noticeItem.getNotice_Title() + " - " + noticeItem.getNotice_Content());
        }

        @SuppressLint("SimpleDateFormat")
        private String timeToStr(Timestamp not_timestamp) {
            // Convert the timestamp to a formatted date and time string
            return new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(not_timestamp.toDate());
        }
    }
}