package com.gipflstuermer.gipfl.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Ammon-Mino on 04.12.2015.
 */
public class GPSSensor implements LocationListener {

    String myLocation;

    public GPSSensor(){

    };

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        location.getAltitude();//mit offset kalibrieren

        myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        Log.d("MY CURRENT LOCATION", myLocation);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
