package com.gipflstuermer.gipfl;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alex on 04.12.15.
 */
public class Trip implements Serializable {

    private String title;
    private String description;
    private String author;
    private String imageURL;
    public ArrayList<PointOfInterest> pois;
    public Route route;
    // public area;

    // Constructor;
    public Trip(String title, String author){
        this.title = title;
        this.author = author;
        this.description = "No description.";
        this.imageURL = null;
    }

    // Getter & Setter
    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void changeName(String name){
        this.title = name;
    }

    public void addImageURL(String imageURL){
        this.imageURL = this.imageURL;
    }

    public void addDescription(String description){
        this.description = description;
    }

}
