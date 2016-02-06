package com.gipflstuermer.gipfl;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

/**
 * Application Level Class to some application wide variables and objects.
 *
 * Created by alex on 08.01.16.
 */
public class MyGipfl extends Application {

    private ArrayList<User> users = new ArrayList<User>(); //<-- For now, all registered Users
    private User currentUser = null;
    private ArrayList<PointOfInterest> poiList = new ArrayList<PointOfInterest>();

    public ArrayList<User> getUserList(){
        return users;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public ArrayList<PointOfInterest> getAllPointsOfInterest(){
        return poiList;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    /**
     * A Method to set the current user from the PREFS_USER variable.
     *
     * @param name should be given by PREFS_USER
     */

    public void setCurrentUser(String name){
        for (User u : users){
            if(u.getName().equals(name)){
                currentUser = u;
            }
        }
    }

    //DEV STUFF

//    public void createContent(){
//        // Creating User and Trips for Development
//        User peter = new User("Peter","1234");
//        peter.addTrip(new Trip("Kaffee-Fahrt","Peter"));
//        peter.addTrip(new Trip("Balkan-Route","Angela"));
//        peter.addTrip(new Trip("Wurst-Wanderung","Peter"));
//        peter.addTrip(new Trip("Walkabout", "Eso-Franz"));
//        users.add(peter);
//
//        //Points of Interest zum Testen
//        poiList.add(new PointOfInterest("Altes Schloss"));
//        poiList.add(new PointOfInterest("Wursthaus"));
//        poiList.add(new PointOfInterest("Erdloch"));
//
//        Log.d("DevContent: ",peter.getName());
//
//
//    }

}
