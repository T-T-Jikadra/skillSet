package com.example.back_benchers.Adpters;

public class Assignment_Item {
    // Declare member variables to store assignment name and URL
    String name, url;

    // Default constructor (empty)
    public Assignment_Item() {
    }

    // Constructor with parameters to initialize assignment name and URL
    public Assignment_Item(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // Getter method to retrieve the assignment name
    public String getName() {
        return name;
    }

    // Setter method to set the assignment name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method to retrieve the assignment URL
    public String getUrl() {
        return url;
    }

    // Setter method to set the assignment URL
    public void setUrl(String url) {
        this.url = url;
    }
}
