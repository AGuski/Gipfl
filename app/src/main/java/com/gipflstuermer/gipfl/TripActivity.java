package com.gipflstuermer.gipfl;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gipflstuermer.gipfl.database.GipflDbHelper;
import com.gipflstuermer.gipfl.tools.Barometer;
import com.gipflstuermer.gipfl.tools.GPSSensor;
import com.gipflstuermer.gipfl.tools.GPXRecorder;

import org.alternativevision.gpx.GPXParser;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.TrackPoint;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class TripActivity extends AppCompatActivity {

    Trip mTrip;
    GipflDbHelper mDbHelper;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new GipflDbHelper(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);

        // Get User & ActiveTrip
        int userId = sharedPreferences.getInt(MainActivity.PREF_USER, 0);
        // If there is no logged in User -> send to Login
        if (userId < 1) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
        User user = mDbHelper.getUser(userId);
        mTrip = user.getActiveTrip();


        // Access Textviews from XML
        TextView tripTitle = (TextView) findViewById(R.id.trip_title);
        tripTitle.setText(mTrip.getTitle());

        final TextView currAltitudeText = (TextView) findViewById(R.id.curr_alti_text);
        final TextView currLongitudeText = (TextView) findViewById(R.id.curr_longi_text);
        final TextView currLatitudeText = (TextView) findViewById(R.id.curr_lati_text);
        final Button GPXrecButton = (Button) findViewById(R.id.gpx_rec_button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        // If User is already logged in, greet him with Alert.
//        if (this.getIntent().hasExtra("User")) {
//            String name = (String) this.getIntent().getExtras().get("User");
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setMessage("Welcome back to your trip, " + name);
//            alert.show();
//        }

        // Add Onscreen GPS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            currAltitudeText.setText(getString(R.string.curr_altitude, "no information"));
            currLongitudeText.setText(getString(R.string.curr_longitude, "no information"));
            currLatitudeText.setText(getString(R.string.curr_latitude, "no information"));
        } else {
            // Get GPS Sensor
            GPSSensor gpsListener = new GPSSensor(this) {
                @Override
                public void onLocationChanged(Location location) {
                    super.onLocationChanged(location);

                    currAltitudeText.setText(getString(R.string.curr_altitude,""+location.getAltitude()));
                    currLongitudeText.setText(getString(R.string.curr_longitude,""+location.getLongitude()));
                    currLatitudeText.setText(getString(R.string.curr_latitude,""+location.getLatitude()));
                }
            };
            gpsListener.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            //gpsListener.getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
        }

        // On Click Listeners

        GPXrecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. Create new Recorder
                final GPXRecorder mGPXRecorder = new GPXRecorder(v.getContext(), 3000);

                //2. start recording
                mGPXRecorder.startRec();

                //3. Show dialog to stop recording
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(R.string.gpx_rec_message)
                        .setTitle(R.string.gpx_rec_title);

                // Stop Recording Button
                builder.setNeutralButton(R.string.stop_rec, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //1. Stop Recording
                        mGPXRecorder.stopRec();
                        GPX mGpx = mGPXRecorder.getGpx();

                        //2. Write GPX to GPX-File
//                        GPXParser gpxParser = new GPXParser();
//                        try {
//                            FileOutputStream out = openFileOutput("thisTrip.gpx", Context.MODE_PRIVATE);
//                            gpxParser.writeGPX(mGpx, out);
//                            out.close();
//                        } catch (IOException | ParserConfigurationException | TransformerException e) {
//                            e.printStackTrace();
//                        }
                    }
                });

                AlertDialog GPXdialog = builder.create();
                GPXdialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_trip_list) {
            Intent tripListIntent = new Intent(this, TripListActivity.class);
            startActivity(tripListIntent);
        }
        if (id == R.id.action_all_pois) {
            Intent allPoisIntent = new Intent(this, PoiActivity.class);
            startActivity(allPoisIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}
