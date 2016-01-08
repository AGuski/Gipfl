package com.gipflstuermer.gipfl;

import java.util.ArrayList;

/**
 * Created by alex on 04.12.15.
 */
public class User {

    private String name;
    private String password;
    private ArrayList<Trip> trips;
    private Trip activeTrip; // if null, no active Trip;
    // more to come...

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.trips = new ArrayList<>();
        this.activeTrip = null;

    }


    // Methods
    public ArrayList<Trip> getTrips(){
        return this.trips;
    }

    public String getName(){
        return this.name;
    }

    public Trip getActiveTrip(){
        return this.activeTrip;
    }

    public void setActiveTrip(Trip trip){
        this.activeTrip = trip;
    }

    public void addTrip(Trip trip){
        this.getTrips().add(trip);
    }

}
