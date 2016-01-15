package com.gipflstuermer.gipfl.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gipflstuermer.gipfl.R;

import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.TrackPoint;
import org.alternativevision.gpx.beans.Waypoint;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * A Class to Record trackpoints to an GPX object
 * Infos for GPX Library: http://gpxparser.alternativevision.ro
 * Created by alex on 14.01.16.
 */
public class GPXRecorder {

    GPX gpx;
    Track gpxTrack;
    int interval;
    ArrayList<Waypoint> trackPoints;
    Context context;
    java.util.Timer t;

    /**
     * Constructor for GPXRecorder
     *
     * @param interval is the interval to record TrackPoints in milliseconds.
     */

    public GPXRecorder(Context context, int interval) {
        this.context = context;
        this.gpx = new GPX();
        this.trackPoints = new ArrayList<>();
        this.gpxTrack = new Track();
        this.gpx.addTrack(gpxTrack);
        this.interval = interval;
        this.t = new java.util.Timer();
    }

    public void startRec(){

        this.trackPoints.add(recordTrackPoint());

        this.t.schedule(new TimerTask() {
            @Override
            public void run() {
                trackPoints.add(recordTrackPoint());
                Log.d("Timer", "Bing!");
            }
        }, this.interval, this.interval);

    }

    public void stopRec(){
        this.t.cancel();
        this.t.purge();
        this.gpxTrack.setTrackPoints(this.trackPoints);
        this.gpx.addTrack(gpxTrack);
    }

    /**
     *
     * @return a Trackpoint with current altitude/elevation, longitude & latitude
     */

    public TrackPoint recordTrackPoint(){
        final TrackPoint trPoint = new TrackPoint();
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            GPSSensor gpsListener = new GPSSensor(this.context);
            gpsListener.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            gpsListener.getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);

            trPoint.setElevation(gpsListener.getAltitude());
            trPoint.setLongitude(gpsListener.getLongitude());
            trPoint.setLatitude(gpsListener.getLatitude());
        }

        return new TrackPoint();
    }

    public GPX getGpx(){
        return this.gpx;
    }

}
