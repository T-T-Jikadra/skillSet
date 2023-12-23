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

import com.example.back_benchers.Admin_Dir.Admin_FeedbackDel;
import com.example.back_benchers.R;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class feedback_ItemAdapter extends RecyclerView.Adapter<feedback_ItemAdapter.ViewHolder> {

    private List<feedback_Item> feedbackItemList;

    // Constructor to initialize the adapter with a list of feedback items
    public feedback_ItemAdapter(List<feedback_Item> feedbackItemList) {
        this.feedbackItemList = feedbackItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Retrieve the feedback item at the given position
        feedback_Item feedback_item = feedbackItemList.get(position);
        // Bind the feedback item data to the view holder
        holder.bind(feedback_item);

        // Set click listener for the item view
        holder.itemView.setOnClickListener(view -> {
            // Create an intent to open the Admin_FeedbackDel activity
            Intent intent = new Intent(view.getContext(), Admin_FeedbackDel.class);
            // Pass data to the intent
            intent.putExtra("feedbackTitle", feedback_item.getFeedback_Title());
            intent.putExtra("feedbackContent", feedback_item.getFeedback_Content());
            intent.putExtra("DocIdFB", feedback_item.getTimestamp().toDate().toString());
            // Start the Admin_FeedbackDel activity
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return feedbackItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView FBtitleTextView;
        private TextView FBcontentTextView;
        private TextView FBTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views within the item view
            FBtitleTextView = itemView.findViewById(R.id.note_title_text_view);
            FBcontentTextView = itemView.findViewById(R.id.note_content_text_view);
            FBTimestamp = itemView.findViewById(R.id.note_timestamp_text_view);
        }

        // Bind data to the views within the view holder
        @SuppressLint("SetTextI18n")
        public void bind(feedback_Item feedback_item) {
            FBtitleTextView.setText(feedback_item.getFeedback_Title());
            FBcontentTextView.setText(feedback_item.getFeedback_Content());
            FBTimestamp.setText(feedback_item.getFeedback_Giver_Sid() + " : " + feedback_item.getFeedback_Giver_Name());

            // Log for debugging
            Log.d("Debug", "Binding: " + feedback_item.getFeedback_Title() + " - " + feedback_item.getFeedback_Content());
        }

        @SuppressLint("SimpleDateFormat")
        // Convert timestamp to a formatted string
        private String timeToStr(Timestamp not_timestamp) {
            return new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(not_timestamp.toDate());
        }
    }
}
