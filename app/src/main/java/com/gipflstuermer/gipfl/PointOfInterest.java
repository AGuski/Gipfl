package com.gipflstuermer.gipfl;

import java.io.Serializable;

/**
 * Created by alex on 03.01.16.
 */
public class PointOfInterest implements Serializable {

    private int id;
    String name;
    String description;
    double longitude;
    double latitude;
    double altitude;
    String imageURL;

    public PointOfInterest(String name, double altitude, double latitude, double longitude) {
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = "No description.";
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    // Should only be set by Database!!
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
