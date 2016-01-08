package com.gipflstuermer.gipfl;

import java.io.Serializable;

/**
 * Created by alex on 03.01.16.
 */
public class PointOfInterest implements Serializable {

    String name;
    String description;
    long longitude;
    long lattitude;
    long altitude;
    String imageURL;

    public PointOfInterest(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
