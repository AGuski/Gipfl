package com.gipflstuermer.gipfl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Access Textviews from XML
        TextView baro_text = (TextView) findViewById(R.id.baro_text);
        baro_text.setText("Wurst");

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

        // Barometer
        Barometer barometer = new Barometer();
        String pressure = Float.toString(barometer.getPressure());
        baro_text.setText(pressure);
    }

}
