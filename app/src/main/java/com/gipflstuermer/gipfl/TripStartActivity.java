package com.gipflstuermer.gipfl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.gipflstuermer.gipfl.database.GipflDbHelper;

import java.util.ArrayList;

public class TripStartActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TripPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Trip mTrip;
    private ArrayList<Trip> mTripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Get the TripList from before
        mTripList = (ArrayList<Trip>)getIntent().getSerializableExtra(MainActivity.TRIPLIST_KEY);
        mSectionsPagerAdapter = new TripPagerAdapter(getSupportFragmentManager(), mTripList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Check if started with an existing Trip, if not, create new Trip:
        if(getIntent().hasExtra(MainActivity.TRIP_KEY)){
            mTrip = (Trip)getIntent().getSerializableExtra(MainActivity.TRIP_KEY);

            Log.d("Trip", "Trip Deserialized!");
            Log.d("Trip", mTrip.getTitle());

            // set the current Item to the trip (WHY YOU NO WORK???!!!!)
            //mViewPager.setCurrentItem(((MyGipfl) getApplication()).getCurrentUser().getTrips().indexOf(mTrip));
        } else {
            // Code for new Trip Here
            Log.d("Trip", "Empty Trip");
        }

        // set the


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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class TripPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Trip> tripList;

        public TripPagerAdapter(FragmentManager fm, ArrayList<Trip> tripList) {
            super(fm);
            this.tripList = tripList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return TripStartFragment.newInstance(this.tripList.get(position));
        }

        @Override
        public int getCount() {
            return this.tripList.size();
        }

    }

    /**
     * The Fragments which contain the Trip start Info
     */

    public static class TripStartFragment extends Fragment {

        GipflDbHelper mDbHelper;
        SharedPreferences sharedPreferences;

        private static final String ARG_TRIP = "trip_start_fragment";
        private Trip mTrip;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TripStartFragment newInstance(Trip trip) {
            TripStartFragment fragment = new TripStartFragment();
            Bundle args = new Bundle();

            args.putSerializable(ARG_TRIP, trip);
            fragment.setArguments(args);
            return fragment;
        }

        public TripStartFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_trip_start, container, false);

            mDbHelper = new GipflDbHelper(getActivity().getApplicationContext());
            sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);

            // <----- VIEW CONTENT FOR START TRIP HERE ------>

            TextView tripTitle = (TextView) rootView.findViewById(R.id.trip_title);
            TextView tripAuthor = (TextView) rootView.findViewById(R.id.trip_author);
            TextView tripDesc = (TextView) rootView.findViewById(R.id.trip_description);

            // Get The Trip as object
            mTrip = (Trip) getArguments().getSerializable(ARG_TRIP);

            // Set the Textfields
            tripTitle.setText(mTrip.getTitle());
            tripAuthor.setText(getString(R.string.by_author, mTrip.getAuthor()));
            tripDesc.setText(mTrip.getDescription());

            // Button
            Button startButton = (Button) rootView.findViewById(R.id.start_trip_button);

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // setTrip as activeTrip for User
                    int userId = sharedPreferences.getInt(MainActivity.PREF_USER,0);
                    mDbHelper.updateActiveTrip(mTrip.getId(),userId);
                    Log.d("Active",mDbHelper.getUser(userId).getActiveTrip().getId()+"");

                    Log.d("startTrip", mTrip.getTitle());
                    // Write in Shared Prefs that user is now on trip!
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(MainActivity.PREF_ONTRIP, true); // is on trip now.
                    editor.commit();

                    Intent tripIntent = new Intent(getActivity(), TripActivity.class);
                    tripIntent.putExtra(MainActivity.TRIP_KEY, mTrip);
                    startActivity(tripIntent);
                }
            });

            // <------ VIEW CONTENT END ------>

            return rootView;
        }
    }
}
