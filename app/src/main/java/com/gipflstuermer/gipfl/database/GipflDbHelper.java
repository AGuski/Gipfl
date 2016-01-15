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

// TODO: NOT CLOSED DATABASE / LOCK DATABASE BUG BEHEBEN.

public class GipflDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
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
                    GipflContract.UserTable.COLUMN_NAME_ACTIVETRIP + INT_TYPE +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + GipflContract.UserTable.TABLE_NAME;


    /* UserTrips Table SQL */

    private static final String SQL_CREATE_USERTRIPS =
            "CREATE TABLE " + GipflContract.UserTripsTable.TABLE_NAME + " (" +
                    GipflContract.UserTable._ID + " INTEGER PRIMARY KEY," +
                    GipflContract.UserTripsTable.COLUMN_NAME_USER_ID + INT_TYPE + COMMA_SEP +
                    GipflContract.UserTripsTable.COLUMN_NAME_TRIP_ID + INT_TYPE +
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
        values.put(GipflContract.UserTable.COLUMN_NAME_ACTIVETRIP, 0);

        // insert row
        long user_id = db.insert(GipflContract.UserTable.TABLE_NAME, "null", values);

        return user_id;
    }

    public long addTripToUser(Trip trip, User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GipflContract.UserTripsTable.COLUMN_NAME_TRIP_ID, trip.getId());
        values.put(GipflContract.UserTripsTable.COLUMN_NAME_USER_ID, user.getId());

        // insert row
        long usertrip_id = db.insert(GipflContract.UserTripsTable.TABLE_NAME, "null", values);

        return usertrip_id;

    }

    public long updateActiveTrip(int tripId, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GipflContract.UserTable.COLUMN_NAME_ACTIVETRIP, tripId);

        // update row
        long user_id = db.update(GipflContract.UserTable.TABLE_NAME, values, GipflContract.UserTable._ID + " = ?",
                new String[]{String.valueOf(userId)});

        return user_id;
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

        return tripBuilder(c);
    }

    public User getUser(long user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + GipflContract.UserTable.TABLE_NAME + " WHERE "
                + GipflContract.UserTable._ID + " = " + user_id;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        return userBuilder(c);
    }

    /**
     *
     * Getting all Table Entries from a Table.
     *
     */

    public ArrayList<Trip> getAllTrips() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Trip> trips = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " +GipflContract.TripTable.TABLE_NAME;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                trips.add(tripBuilder(c));
            } while (c.moveToNext());
        }
        return trips;
    }

    /**
     *
     * Getting Entries from a Table belonging to a User
     *
     */

    public ArrayList<Trip> getTripsOfUser(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Trip> trips = new ArrayList<>();

        String TripTableName = GipflContract.TripTable.TABLE_NAME;
        String UserTripsTableName = GipflContract.UserTripsTable.TABLE_NAME;

        String selectQuery = "SELECT " + TripTableName + ".*"
                + " FROM " + UserTripsTableName
                + " LEFT JOIN " + TripTableName
                + " ON " + TripTableName + "." + GipflContract.TripTable._ID + " = "
                + GipflContract.UserTripsTable.COLUMN_NAME_TRIP_ID
                + " WHERE " + GipflContract.UserTripsTable.COLUMN_NAME_USER_ID + " = " + UserId;

        Log.d("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                trips.add(tripBuilder(c));
            } while (c.moveToNext());
        }
        return trips;
    }

    /**
     *
     * Builders to make Objects from cursor position in the database.
     * Must be updated when Attributes change.
     *
     */

    private Trip tripBuilder(Cursor c){
        String title = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_TITLE));
        String author = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_AUTHOR));
        String description = c.getString(c.getColumnIndex(GipflContract.TripTable.COLUMN_NAME_DESCRIPTION));
        Trip trip = new Trip(title, author);
        trip.setId(c.getInt(c.getColumnIndex(GipflContract.TripTable._ID)));

        return trip;
    }

    private User userBuilder(Cursor c){

        String name = c.getString(c.getColumnIndex(GipflContract.UserTable.COLUMN_NAME_NAME));
        String password = c.getString(c.getColumnIndex(GipflContract.UserTable.COLUMN_NAME_PASSWORD));

        User user = new User(name,password);
        user.setId(c.getInt(c.getColumnIndex(GipflContract.UserTable._ID)));
        int activeTrip = c.getInt(c.getColumnIndex(GipflContract.UserTable.COLUMN_NAME_ACTIVETRIP));
        // If user has an active trip, get trip from database and set it
        if (activeTrip > 0) {
            user.setActiveTrip(getTrip(c.getInt(c.getColumnIndex(GipflContract.UserTable.COLUMN_NAME_ACTIVETRIP))));
        }

        return user;

    }

}
