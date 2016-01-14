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

import com.gipflstuermer.gipfl.TripActivity;

/**
 * Created by Ammon-Mino on 04.12.2015.
 */
public class GPSSensor implements LocationListener {

    String mAltitude;
    String mLongitude;
    String mLatitude;

    public GPSSensor(){

    };

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = ""+location.getLatitude();
        mLongitude = ""+location.getLongitude();
        mAltitude = ""+location.getAltitude();//mit offset kalibrieren;

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
