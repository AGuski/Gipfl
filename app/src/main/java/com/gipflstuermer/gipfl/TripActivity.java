package com.gipflstuermer.gipfl;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.gipflstuermer.gipfl.tools.Barometer;
import com.gipflstuermer.gipfl.tools.GPSSensor;

public class TripActivity extends AppCompatActivity {

    private final static String TRIP_KEY = "com.giflstuermer.gipfl.trip_key";
    Trip mTrip;
    String currAltitude;
    String currLongitude;
    String currLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check if started with an existing Trip, if not: ERROR!
        if(getIntent().hasExtra(TRIP_KEY)){
            mTrip = (Trip)getIntent().getSerializableExtra(TRIP_KEY);
            Log.d("Trip", "Trip Deserialized!");
            Log.d("Trip", mTrip.getTitle());
        } else {
            Log.d("Trip", "Empty Trip");
        }

        // get currentTrip from Global Variable
        mTrip = ((MyGipfl) getApplication()).getCurrentUser().getActiveTrip();

        // Access Textviews from XML
        TextView tripTitle = (TextView) findViewById(R.id.trip_title);
        tripTitle.setText(mTrip.getTitle());

        final TextView currAltitudeText = (TextView) findViewById(R.id.curr_alti_text);
        final TextView currLongitudeText = (TextView) findViewById(R.id.curr_longi_text);
        final TextView currLatitudeText = (TextView) findViewById(R.id.curr_lati_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // If User is already logged in, greet him with Alert.
        if (this.getIntent().hasExtra("User")) {
            String name = (String) this.getIntent().getExtras().get("User");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Welcome back to your trip, " + name);
            alert.show();
        }

        // Add Barometer

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            currAltitudeText.setText(getString(R.string.curr_altitude, "no information"));
            currLongitudeText.setText(getString(R.string.curr_longitude, "no information"));
            currLatitudeText.setText(getString(R.string.curr_latitude, "no information"));
        } else {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            // Get GPS Sensor
            GPSSensor gpsListener = new GPSSensor() {
                @Override
                public void onLocationChanged(Location location) {
                    super.onLocationChanged(location);

                    currAltitudeText.setText(getString(R.string.curr_altitude,""+location.getAltitude()));
                    currLongitudeText.setText(getString(R.string.curr_longitude,""+location.getLongitude()));
                    currLatitudeText.setText(getString(R.string.curr_latitude,""+location.getLatitude()));

                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);

        }

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
