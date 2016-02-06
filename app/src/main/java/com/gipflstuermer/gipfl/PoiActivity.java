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

public class PoiActivity extends AppCompatActivity {

    GipflDbHelper mDbHelper;
    SharedPreferences sharedPreferences;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PoiPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private PointOfInterest mPoi;
    private ArrayList<PointOfInterest> mPoiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new GipflDbHelper(getApplicationContext());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mPoiList = mDbHelper.getAllPois();
        mSectionsPagerAdapter = new PoiPagerAdapter(getSupportFragmentManager(), mPoiList);

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poi, menu);
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

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PoiPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<PointOfInterest> poiList;

        public PoiPagerAdapter(FragmentManager fm, ArrayList<PointOfInterest> poiList) {
            super(fm);
            this.poiList = poiList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PoiFragment.newInstance(poiList.get(position));
        }

        @Override
        public int getCount() {
            return poiList.size();
        }

    }

    /**
     * The Fragments which contain the Trip start Info
     */

    public static class PoiFragment extends Fragment {

        private static final String PREFS = "prefs";
        private static final String PREF_ONTRIP = "OnTrip"; // <-- boolean, if the user is on trip
        SharedPreferences sharedPreferences;

        private static final String ARG_POI = "poi_fragment";
        private PointOfInterest mPoi;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PoiFragment newInstance(PointOfInterest poi) {
            PoiFragment fragment = new PoiFragment();
            Bundle args = new Bundle();

            args.putSerializable(ARG_POI, poi);
            fragment.setArguments(args);
            return fragment;
        }

        public PoiFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_poi, container, false);

            sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

            // <----- VIEW CONTENT FOR POINTS OF INTEREST HERE ------>

            TextView poiName = (TextView) rootView.findViewById(R.id.poi_name);

            // Get The poi as object
            mPoi = (PointOfInterest) getArguments().getSerializable(ARG_POI);

            // Set the Textfields
            poiName.setText(mPoi.getName());

            // <------ VIEW CONTENT END ------>

            return rootView;
        }
    }
}
