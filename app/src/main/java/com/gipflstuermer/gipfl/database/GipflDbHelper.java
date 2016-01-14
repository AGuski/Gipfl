package com.gipflstuermer.gipfl.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gipflstuermer.gipfl.Trip;
import com.gipflstuermer.gipfl.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 13.01.16.
 */
public class GipflDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = "INT";
    private static final String COMMA_SEP = ",";

    /* Trips Table SQL */

    private static final String SQL_CREATE_TRIPS =
            "CREATE TABLE " + GipflContract.TripTable.TABLE_NAME + " (" +
                    GipflContract.TripTable._ID + " INTEGER PRIMARY KEY," +
                    GipflContract.TripTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    GipflContract.TripTable.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    GipflContract.TripTable.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_TRIPS =
            "DROP TABLE IF EXISTS " + GipflContract.TripTable.TABLE_NAME;


    /* Users Table SQL */

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + GipflContract.UserTable.TABLE_NAME + " (" +
                    GipflContract.UserTable._ID + " INTEGER PRIMARY KEY," +
                    GipflContract.UserTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    GipflContract.UserTable.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + GipflContract.UserTable.TABLE_NAME;


    /* UserTrips Table SQL */

    private static final String SQL_CREATE_USERTRIPS =
            "CREATE TABLE " + GipflContract.UserTable.TABLE_NAME + " (" +
                    GipflContract.UserTable._ID + " INTEGER PRIMARY KEY," +
                    GipflContract.UserTripsTable.COLUMN_NAME_USER_ID + INT_TYPE + COMMA_SEP +
                    GipflContract.UserTripsTable.COLUMN_NAME_TRIP_ID + INT_TYPE + COMMA_SEP +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_USERTRIPS =
            "DROP TABLE IF EXISTS " + GipflContract.UserTripsTable.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Gipfl.db";

    public GipflDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRIPS);
        Log.d("db: ", SQL_CREATE_TRIPS);
        db.execSQL(SQL_CREATE_USERS);
        Log.d("db: ", SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_USERTRIPS);
        Log.d("db: ", SQL_CREATE_USERTRIPS);


        /*
         * FILL DATABASE ONLY FOR DEVELOPMENT -> LATER TO BE REPLACED WITH ONLINE DB-CONNECTION
         */

        this.createUser(new User("Peter", "1234"));


        this.createTrip(new Trip("Kaffee-Fahrt", "Peter"));
        this.createTrip(new Trip("Balkan-Route","Angela"));
        this.createTrip(new Trip("Wurst-Wanderung", "Peter"));
        this.createTrip(new Trip("Walkabout", "Eso-Franz"));

        for (Trip trip : this.getAllTrips()) {
            Log.d("Title", trip.getTitle());
            Log.d("Author", trip.getAuthor());

        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TRIPS);
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_USERTRIPS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Manipulating the Database

    /**
     *
     * Creating Table Entries
     *
     */

    public long createTrip(Trip trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GipflContract.TripTable.COLUMN_NAME_TITLE, trip.getTitle());
        values.put(GipflContract.TripTable.COLUMN_NAME_AUTHOR, trip.getAuthor());
        values.put(GipflContract.TripTable.COLUMN_NAME_DESCRIPTION, trip.getDescription());

        // insert row
        long trip_id = db.insert(GipflContract.TripTable.TABLE_NAME, "null", values);

        return trip_id;
    }

    public long createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GipflContract.UserTable.COLUMN_NAME_NAME, user.getName());
        values.put(GipflContract.UserTable.COLUMN_NAME_PASSWORD, user.getPassword());

        // insert row
        long trip_id = db.insert(GipflContract.UserTable.TABLE_NAME, "null", values);

        return trip_id;
    }

    /**
     *
     * Getting single Table Entries by Id
     *
     */

    public Trip getTrip(long trip_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + GipflContract.TripTable.TABLE_NAME + " WHERE "
                + GipflContract.TripTable._ID + " = " + trip_id;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String title = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_TITLE));
        String author = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_AUTHOR));
        String description = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_DESCRIPTION));

        Trip trip = new Trip(title,author);
        trip.setId(c.getInt(c.getColumnIndex(GipflContract.TripTable._ID)));

        return trip;
    }

    /**
     *
     * Getting all Table Entries from a Table.
     *
     */

    public List<Trip> getAllTrips() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Trip> trips = new ArrayList<Trip>();
        String selectQuery = "SELECT  * FROM " +GipflContract.TripTable.TABLE_NAME;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_TITLE));
                String author = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_AUTHOR));
                String description = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_DESCRIPTION));
                Trip t = new Trip(title, author);

                // adding to trip list
                trips.add(t);
            } while (c.moveToNext());
        }
        return trips;
    }

    /**
     *
     * Getting Entries from a Table belonging to a User
     *
     */

    public List<Trip> getTripsOfUser(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Trip> trips = new ArrayList<Trip>();
        // <--- was nu? SQL --->
        String selectQuery = "SELECT  * FROM " +GipflContract.TripTable.TABLE_NAME;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_TITLE));
                String author = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_AUTHOR));
                String description = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_DESCRIPTION));
                Trip t = new Trip(title, author);

                // adding to trip list
                trips.add(t);
            } while (c.moveToNext());
        }
        return trips;
    }
}
