package com.gipflstuermer.gipfl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gipflstuermer.gipfl.database.GipflDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Shared Preferences:
    SharedPreferences sharedPreferences;
    public static final String PREFS = "prefs";
    public static final String PREF_USER =  "UserId"; // <-- The Logged in User as String
    public static final String PREF_ONTRIP = "OnTrip"; // <-- boolean, if the user is on trip

    // Extra Keys used in the app
    public static final String TRIP_KEY = "com.giflstuermer.gipfl.trip_key"; // <-- key for trip to put as extra.
    public static final String TRIPLIST_KEY = "com.giflstuermer.gipfl.triplist_key";

    // Database
    GipflDbHelper mDbHelper;

    // TODO: Change sharedPref

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        // Get Shared Prefs:
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        // Get Database:
        mDbHelper = new GipflDbHelper(getApplicationContext());

        // Set SharedPrefs for development!
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_USER, 1); // Logged in as Peter (user id 1).

        //((MyGipfl) this.getApplication()).createContent(); //<-- Creating content in Dev.

        // set Current User
        //((MyGipfl) this.getApplication()).setCurrentUser(sharedPreferences.getString(PREF_USER, ""));
        //((MyGipfl) this.getApplication()).setCurrentUser("Peter");

        editor.putBoolean(PREF_ONTRIP, false); // is on trip. Switch for Trip/TripList
        //editor.clear(); // <-- Clears the SharedPrefs - For Development!
        editor.commit();

        //testContent(this);

        Log.d("CurrUser", "ID: " + mDbHelper.getUser(1).getId() + " Name:" + mDbHelper.getUser(1).getName());


        // Check for Login Session
        if (sharedPreferences.getInt(PREF_USER, 0) == 0) {
            // Show Login Screen
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else if(sharedPreferences.getBoolean(PREF_ONTRIP, false)){
            // If ONTRIP = true -> Show Trip Screen
            Intent tripIntent = new Intent(this, TripActivity.class);
            startActivity(tripIntent);
        } else {
            // Show Trip List
            Intent tripListIntent = new Intent(this, TripListActivity.class);
            startActivity(tripListIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_active_trip) {
            Intent tripIntent = new Intent(this, TripActivity.class);
            startActivity(tripIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    /* Test Content */

    public static void testContent(Context context){
        /*
         * FILL DATABASE ONLY FOR DEVELOPMENT -> LATER TO BE REPLACED WITH ONLINE DB-CONNECTION
         */

        // TODO: Bessere Möglichkeit gleichzeitig Objekte und DB-Einträge zu verwalten.

        GipflDbHelper mDbHelper = new GipflDbHelper(context);

        mDbHelper.createUser(new User("Peter", "1234"));

        mDbHelper.createTrip(new Trip("Kaffee-Fahrt", "Peter"));
        mDbHelper.createTrip(new Trip("Balkan-Route", "Angela"));
        mDbHelper.createTrip(new Trip("Wurst-Wanderung", "Peter"));
        mDbHelper.createTrip(new Trip("Walkabout", "Eso-Franz"));
        mDbHelper.createTrip(new Trip("Lustige Wanderung", "Vincent"));
        mDbHelper.createTrip(new Trip("Pilgerfahrt", "Ibrahim"));

        mDbHelper.addTripToUser(mDbHelper.getTrip(1), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(2), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(3), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(4), mDbHelper.getUser(1));

        mDbHelper.updateActiveTrip(mDbHelper.getTrip(2).getId(),mDbHelper.getUser(1).getId());

//        for (Trip trip : mDbHelper.getTripsOfUser(1)) {
//            Log.d("Title", trip.getTitle());
//            Log.d("Author", trip.getAuthor());
//        }

        Log.d("DEV","Content Created");
    }

}
