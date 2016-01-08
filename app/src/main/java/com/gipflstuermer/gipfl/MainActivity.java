package com.gipflstuermer.gipfl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*public ArrayList<User> users; //<-- For now, all registered Users
    public User currentUser;*/

    // Pesistent storage +-+
    private static final String PREFS = "prefs";
    private static final String PREF_USER =  "User"; // <-- The Logged in User as String
    private static final String PREF_ONTRIP = "OnTrip"; // <-- boolean, if the user is on trip
    SharedPreferences sharedPreferences;

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

        // Creating User and Trips for Development
//        User peter = new User("Peter","1234");
//        peter.addTrip(new Trip("Kaffee-Fahrt","Peter"));
//        peter.addTrip(new Trip("Balkan-Route","Angela"));
//        users.add(peter);

        // Set SharedPrefs for development!
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER, "Peter"); // Logged in as Peter.

        // set Current User
        /*for (User u : users){
            if(u.getName().equals(sharedPreferences.getString(PREF_USER, ""))){
                currentUser = u;
            } else {
                currentUser = peter;
            }
        }*/

        editor.putBoolean(PREF_ONTRIP, true); // is on trip. Switch for Trip/TripList
        //editor.clear(); // <-- Clears the SharedPrefs - For Development!
        editor.commit();

        // Check for Login Session
        if(sharedPreferences.getString(PREF_USER, null) == null) {
            // Show Login Screen
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else if(sharedPreferences.getBoolean(PREF_ONTRIP, false)){
            // Show Trip Screen
            Intent tripIntent = new Intent(this, TripActivity.class);
            tripIntent.putExtra("User",sharedPreferences.getString(PREF_USER, ""));
            startActivity(tripIntent);
        } else {
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

}
