package com.example.back_benchers.Adpters;

public class Image_Item {
    private String imageName;
    private String imageUrl;

    public Image_Item() {
    }

    public Image_Item(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
