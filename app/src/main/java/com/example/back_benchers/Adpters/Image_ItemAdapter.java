package com.example.back_benchers.Adpters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.back_benchers.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Image_ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Image_Item> imageItems;

    // Constructor to initialize the adapter with context and image items
    public Image_ItemAdapter(Context context, List<Image_Item> imageItems) {
        this.context = context;
        this.imageItems = imageItems;
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_list_item_image, parent, false);
        }
        int reversePosition = getCount() - position - 1;
        ImageView imageView = view.findViewById(R.id.imageView);
        Image_Item imageItem = imageItems.get(reversePosition);

        // Load the image using Picasso
        Picasso.get().load(imageItem.getImageUrl()).into(imageView);

        // Set a click listener to open the image in the system image viewer
        imageView.setOnClickListener(v -> {
            String imageUrl = imageItem.getImageUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(imageUrl), "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        });

        // Set a long click listener on the image view
        imageView.setOnLongClickListener(v -> {
            showPopupMenu(imageView, imageItem.getImageUrl());
            return true;
        });

        return view;
    }

    // Show the popup menu for the image view
    private void showPopupMenu(View view, String imageUrl) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_imagedownload, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_download) {
                // Download logic
                downloadImage(imageUrl);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    // Download the image using Glide and save to device storage
    private void downloadImage(String imageUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Generate a unique filename using current timestamp
                        String fileName = "BackBenchers_" + System.currentTimeMillis() + ".jpg";
                        File file = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS), fileName);

                        // Save the bitmap to the file on the device
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            // Notify the media scanner to index the new image
                            MediaScannerConnection.scanFile(context,
                                    new String[] { file.getAbsolutePath() },
                                    null,
                                    (path, uri) -> {
                                        // Display a toast indicating successful download
                                        Toast.makeText(context,
                                                        "Image downloaded successfully",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Display a toast indicating download error
                            Toast.makeText(context,
                                            "Image download failed",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });
    }
}
