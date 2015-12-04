package com.gipflstuermer.gipfl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * This Class gives access to a list of trips (via the awesome custom tripAdapter)
 * and the function to create a new trip.
 * If a trip is chosen or a new one created, the TripStartActivity will be started
 * where the trip-details can be viewed and edited.
 **/

public class TripListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    ListView mainListView;
    TripAdapter tripAdapter;

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

        // 1. Access the ListView
        mainListView = (ListView) findViewById(R.id.trip_listview);

        // 2. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);

        // 3. Create a TripAdapter for the ListView
        tripAdapter = new TripAdapter(this, getLayoutInflater());

        // 4. Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(tripAdapter);

        //Test Trips & Liste
        Trip trip1 = new Trip("Kaffee-Fahrt","Peter");
        Trip trip2 = new Trip("Balkan-Route","Angela");

        ArrayList<Trip> tripList = new ArrayList<>();
        tripList.add(trip1);
        tripList.add(trip2);
        tripAdapter.updateData(tripList);

    }

    // OnClick Listener for the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Go to TripStart Screen
        Intent tripStartIntent = new Intent(this, TripStartActivity.class);
        startActivity(tripStartIntent);
    }
}
