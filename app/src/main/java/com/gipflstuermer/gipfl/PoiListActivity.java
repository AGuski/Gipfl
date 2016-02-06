package com.gipflstuermer.gipfl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gipflstuermer.gipfl.database.GipflDbHelper;

import java.util.ArrayList;

/**
 * This Class gives access to a list of trips (via the awesome custom poiAdapter)
 * and the function to create a new poi.
 * If a poi is chosen or a new one created, the PoiStartActivity will be started
 * where the poi-details can be viewed and edited.
 **/

public class PoiListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    GipflDbHelper mDbHelper;
    SharedPreferences sharedPreferences;

    ListView mainListView;
    PoiAdapter poiAdapter;

    private ArrayList<PointOfInterest> poiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_list);
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

        // 1. Access the ListView
        mainListView = (ListView) findViewById(R.id.poi_listview);

        // 2. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);

        // 3. Create a PoiAdapter for the ListView
        poiAdapter = new PoiAdapter(this, getLayoutInflater());

        // 4. Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(poiAdapter);

        // 5. get Poi list from currentUser <-- at the moment: all pois;
        poiList = mDbHelper.getAllPois();

        // 6. Update the PoiAdapter
        poiAdapter.updateData(poiList);

    }

    // OnClick Listener for the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1. serialize selected Poi to send to next Activity
        PointOfInterest selectedPoi = (PointOfInterest) poiAdapter.getItem(position);
        // 2. Go to PoiStart Screen
        Intent poiIntent = new Intent(this, PoiActivity.class);
        poiIntent.putExtra(MainActivity.POI_KEY,selectedPoi);
        poiIntent.putExtra(MainActivity.POILIST_KEY, poiList);
        startActivity(poiIntent);
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
                Intent poiIntent = new Intent(this, PoiActivity.class);
                startActivity(poiIntent);
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
