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

public class MainActivity extends AppCompatActivity {

    // Shared Preferences:
    SharedPreferences sharedPreferences;
    public static final String PREFS = "prefs";
    public static final String PREF_USER =  "UserId"; // <-- The Logged in User as String
    public static final String PREF_ONTRIP = "OnTrip"; // <-- boolean, if the user is on trip

    // Extra Keys used in the app
    public static final String TRIP_KEY = "com.giflstuermer.gipfl.trip_key"; // <-- key for trip to put as extra.
    public static final String TRIPLIST_KEY = "com.giflstuermer.gipfl.triplist_key";
    public static final String POI_KEY = "com.giflstuermer.gipfl.poi_key"; // <-- key for trip to put as extra.
    public static final String POILIST_KEY = "com.giflstuermer.gipfl.poilist_key";

    // Database
    GipflDbHelper mDbHelper;

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

        editor.putBoolean(PREF_ONTRIP, false); // is on trip. Switch for Trip/TripList
        //editor.clear(); // <-- Clears the SharedPrefs - For Development!
        editor.commit();

        //testContent(this); //<---- ZUM ERSTEN START NUTZEN - DANACH AUSKOMMENTIEREN!!

        // DEBUG LOG
        Log.d("CurrUser", "ID: " + mDbHelper.getUser(1).getId() + " Name:" + mDbHelper.getUser(1).getName());

        // Check if a User is logged in, if not UserID = 0
        if (sharedPreferences.getInt(PREF_USER, 0) == 0) {
            // If not Logged in.
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else if(sharedPreferences.getBoolean(PREF_ONTRIP, false)){
            // If logged in & on trip -> TripActivity
            Intent tripIntent = new Intent(this, TripActivity.class);
            startActivity(tripIntent);
        } else {
            // If logged in & not on trip -> TripListActivity
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
        if (id == R.id.action_log_out) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(PREF_USER, 0); // User Id 0 = logged Out.

            editor.putBoolean(PREF_ONTRIP, false); // is on trip. Switch for Trip/TripList
            //editor.clear(); // <-- Clears the SharedPrefs - For Development!
            editor.commit();

            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);


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
        mDbHelper.createTrip(new Trip("Berlin Tour", "Angela"));
        mDbHelper.createTrip(new Trip("Wurst-Wanderung", "Peter"));
        mDbHelper.createTrip(new Trip("Walkabout", "Eso-Franz"));
        mDbHelper.createTrip(new Trip("Lustige Wanderung", "Vincent"));
        mDbHelper.createTrip(new Trip("Pilgerfahrt", "Ibrahim"));

        PointOfInterest poi1 = new PointOfInterest("Fernsehturm", 13.00, 52.520815, 13.409430);
        poi1.setImageURL("android.resource://com.gipflstuermer.gipfl/drawable/poi_ft_img");
        poi1.setDescription("Ein sehr, sehr pitoresker Turm im Stile der 60er Jahre gehalten.");

        PointOfInterest poi2 = new PointOfInterest("Brandenburger Tor", 12.00, 52.516389, 13.377778);
        poi2.setImageURL("android.resource://com.gipflstuermer.gipfl/drawable/poi_bt_img");
        poi2.setDescription("Ein schönes Tor, nur führt es leider gar nicht nach Brandenburg. Das ist sehr schade.");

        PointOfInterest poi3 = new PointOfInterest("Beuth Hochschule für Technik", 16.00, 52.545336, 13.351633);
        poi3.setImageURL("android.resource://com.gipflstuermer.gipfl/drawable/poi_beuth_img");
        poi3.setDescription("Hier kann man so richtig schön Medieninformatik studieren.");

        PointOfInterest poi4 = new PointOfInterest("Siegessäule", 10.00, 52.514688, 13.350104);
        poi4.setImageURL("android.resource://com.gipflstuermer.gipfl/drawable/poi_ss_img");
        poi4.setDescription("Die Siegessäule auf dem Großen Stern inmitten des Großen Tiergartens in Berlin " +
                "wurde von 1864 bis 1873 als Nationaldenkmal der Einigungskriege nach einem Entwurf " +
                "von Heinrich Strack erbaut.");

        PointOfInterest poi5 = new PointOfInterest("Beuth Hochschule für Technik", 19.00, 52.543449, 13.359516);
        poi5.setImageURL("android.resource://com.gipflstuermer.gipfl/drawable/poi_eb_img");
        poi5.setDescription("Im Februar gibt es hier Rauchbier. Das ist alles, was man wissen muss.");

        mDbHelper.createPoi(poi1);
        mDbHelper.createPoi(poi2);
        mDbHelper.createPoi(poi3);
        mDbHelper.createPoi(poi4);
        mDbHelper.createPoi(poi5);



//        for (PointOfInterest poi : mDbHelper.getAllPois()) {
//            Log.d("Lati",""+poi.getLatitude());
//        }

        mDbHelper.addTripToUser(mDbHelper.getTrip(1), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(2), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(3), mDbHelper.getUser(1));
        mDbHelper.addTripToUser(mDbHelper.getTrip(4), mDbHelper.getUser(1));

        mDbHelper.updateActiveTrip(mDbHelper.getTrip(2).getId(),mDbHelper.getUser(1).getId());

//        for (Trip trip : mDbHelper.getTripsOfUser(1)) {
//            Log.d("Title", trip.getTitle());
//            Log.d("Author", trip.getAuthor());
//        }

        Log.d("DEV", "Content Created");
    }

}
