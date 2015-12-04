package com.gipflstuermer.gipfl;

import android.media.Image;

/**
 * Created by alex on 04.12.15.
 */
public class Trip {

    private String title;
    private String description;
    private String author;
    private Image image;
    // public PointOfInterest[] pois;
    // public Route;
    // public area;

    // Constructor;
    public Trip(String title, String author){
        this.title = title;
        this.author = author;
        this.description = "No description.";
        this.image = null;
    }

    // Getter & Setter
    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public Image getImage() {
        return image;
    }

    public void changeName(String name){
        this.title = name;
    }

    public void addImage(Image image){
        this.image = image;
    }

    public void addDescription(String description){
        this.description = description;
    }


}
