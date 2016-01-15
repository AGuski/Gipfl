package com.gipflstuermer.gipfl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gipflstuermer.gipfl.database.GipflContract;
import com.gipflstuermer.gipfl.database.GipflDbHelper;

import java.util.ArrayList;

/**
 * This Class gives access to a list of trips (via the awesome custom tripAdapter)
 * and the function to create a new trip.
 * If a trip is chosen or a new one created, the TripStartActivity will be started
 * where the trip-details can be viewed and edited.
 **/

public class TripListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    GipflDbHelper mDbHelper;
    SharedPreferences sharedPreferences;

    ListView mainListView;
    TripAdapter tripAdapter;

    private ArrayList<Trip> tripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new GipflDbHelper(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);

        // 1. Access the ListView
        mainListView = (ListView) findViewById(R.id.trip_listview);

        // 2. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);

        // 3. Create a TripAdapter for the ListView
        tripAdapter = new TripAdapter(this, getLayoutInflater());

        // 4. Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(tripAdapter);

        // 5. get Trip list from currentUser <-- at the moment: all trips;
        tripList = mDbHelper.getAllTrips();

        // 6. Update the TripAdapter
        tripAdapter.updateData(tripList);

    }

    // OnClick Listener for the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1. serialize selected Trip to send to next Activity
        Trip selectedTrip = (Trip) tripAdapter.getItem(position);
        // 2. Go to TripStart Screen
        Intent tripStartIntent = new Intent(this, TripStartActivity.class);
        tripStartIntent.putExtra(MainActivity.TRIP_KEY,selectedTrip);
        tripStartIntent.putExtra(MainActivity.TRIPLIST_KEY, tripList);
        startActivity(tripStartIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_list, menu);
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

        if (id == R.id.action_active_trip) {
            if (sharedPreferences.getBoolean(MainActivity.PREF_ONTRIP, false)) {
                Intent tripIntent = new Intent(this, TripActivity.class);
                startActivity(tripIntent);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("No Active Trip!");
                alert.show();
            }
        }

        if (id == R.id.action_all_pois) {
            Intent allPoisIntent = new Intent(this, PoiActivity.class);
            startActivity(allPoisIntent);
        }

        return super.onOptionsItemSelected(item);
    }


}
