package com.gipflstuermer.gipfl.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by alex on 13.01.16.
 */
public final class GipflContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public GipflContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TripTable implements BaseColumns {
        public static final String TABLE_NAME = "trips";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }

    public static abstract class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_ACTIVETRIP = "active_trip";

    }

    public static abstract class PoiTable implements BaseColumns {
        public static final String TABLE_NAME = "pois";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_ALTITUDE = "altitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    }

    // Ein User hat mehrere Trips
    public static abstract class UserTripsTable implements BaseColumns {
        public static final String TABLE_NAME = "userTrips";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_TRIP_ID = "trip_id";

    }

    // Ein Trip hat mehrere Pois
    public static abstract class TripPoisTable implements BaseColumns {
        public static final String TABLE_NAME = "TripPois";
        public static final String COLUMN_NAME_TRIP_ID = "trip_id";
        public static final String COLUMN_NAME_POI_ID = "poi_id";

    }



}
