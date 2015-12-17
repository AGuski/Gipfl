package com.gipflstuermer.gipfl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gipflstuermer.gipfl.tools.Barometer;

import java.util.Observable;
import java.util.Observer;

public class TripActivity extends AppCompatActivity implements Observer{

    private final static String TRIP_KEY = "com.giflstuermer.gipfl.trip_key";
    Trip mTrip;
    private TextView baro_text;
    private Barometer barometer = new Barometer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Access Textviews from XML
        baro_text = (TextView) findViewById(R.id.baro_text);
        //baro_text.setText("Wurst");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Check if started with an existing Trip, if not: ERROR!
        if(getIntent().hasExtra(TRIP_KEY)){
            mTrip = (Trip)getIntent().getSerializableExtra(TRIP_KEY);
            Log.d("Trip", "Trip Deserialized!");
            Log.d("Trip", mTrip.getTitle());
        } else {
            Log.d("Trip", "Empty Trip");
        }

        // If User is already logged in, greet him with Alert.
        if (this.getIntent().hasExtra("User")) {
            String name = (String) this.getIntent().getExtras().get("User");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Welcome back to your trip, " + name);
            alert.show();
        }

        // Add Barometer
        barometer.addObserver(this);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg) {
        String pressure = Float.toString(barometer.getPressure());
        baro_text.setText(pressure);
    }

}
