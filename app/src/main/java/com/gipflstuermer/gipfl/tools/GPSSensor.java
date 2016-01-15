package com.gipflstuermer.gipfl.tools;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gipflstuermer.gipfl.TripActivity;

/**
 * Created by Ammon-Mino on 04.12.2015.
 */
public class GPSSensor implements LocationListener {

    private double mAltitude;
    private double mLongitude;
    private double mLatitude;
    private LocationManager locationManager;

    public GPSSensor(Context context){

        // Create Location Manager
        this.locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

    };

    @Override
    public void onLocationChanged(Location location) {
        Log.d("GPS","Changed!");
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        mAltitude = location.getAltitude();//mit offset kalibrieren;

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

    public LocationManager getLocationManager() {
        return this.locationManager;
    }


    /**
     * Getter for Altitude
     * @return the last recorded values
     */

    public double getAltitude(){
        return this.mAltitude;
    }

    public double getLongitude(){
        return this.mLongitude;
    }
    public double getLatitude(){
        return this.mLatitude;
    }


}
