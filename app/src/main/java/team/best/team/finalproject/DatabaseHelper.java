package team.best.team.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Joel on 27/11/2017.
 * <p>
 * When adding a new table, please follow similar style to the way Thermostat is used
 * ie New tables need constants for table name, columns, and array of columns
 * New tables need to be added to onCreate()
 * New tables need a public get___DataFromDB, add___DataToDB, ?remove___DataToDB
 * This could become a superclass/interface then ThermostatDatabaseHelper would implement DatabaseHelper
 * I think keeping it as 1 class works since we'll only be using 1 static instance of the database throughout the app
 * ie Main Activity instantiates a new DatabaseHelper and it is used everywhere.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static SQLiteDatabase database;
    
    private static final String ACTIVITY_NAME = "DatabaseHelper";
    
    private static final String DATABASE_NAME = "BEST_DATABASE.db";
    private static final int VERSION_NUM = 8;
    
    private static final String KEY_ID = "_ID"; // _ID is used by all tables

    //THERMOSTAT TABLE
    private static final String THERMOSTAT_TABLE_NAME = "THERMOSTAT_TABLE";
    private static final String KEY_THERMOSTAT_DAY = "DAY";
    private static final String KEY_THERMOSTAT_TIME = "HOUR";
    private static final String KEY_THERMOSTAT_TEMPERATURE = "TEMPERATURE";
    private static final String[] THERMOSTAT_COLUMNS = {KEY_THERMOSTAT_DAY, KEY_THERMOSTAT_TIME, KEY_THERMOSTAT_TEMPERATURE}; // columns does not include KEY_ID

    //ACTIVITY TRACKER TABLE
    private static final String ACTIVITY_TABLE_NAME = "ACTIVITY_TABLE";
    private static final String KEY_ACTIVITY_DAY = "DAY";
    private static final String KEY_ACTIVITY_TIME = "TIME";
    private static final String KEY_ACTIVITY_ACTIVITY = "ACTIVITY";
    private static final String KEY_ACTIVITY_NOTES = "NOTES";
    private static final String[] ACTIVITY_COLUMNS = {KEY_ACTIVITY_DAY, KEY_ACTIVITY_TIME, KEY_ACTIVITY_ACTIVITY, KEY_ACTIVITY_NOTES}; // columns does not include KEY_ID

    //AUTOMOBILE TABLE
    private static final String AUTOMOBILE_TABLE_NAME = "AUTOMOBILE_TABLE";
    private static final String KEY_AUTOMOBILE_TYPE = "CAR";
    private static final String KEY_GAS_TYPE = "GAS";
    private static final String KEY_GAS_PRICE = "PRICE";
    private static final String KEY_GAS_LITERS = "LITERS";
    private static final String KEY_AVG_KM = "AVGKM";
    private static final String KEY_DATE = "DATE";
    private static final String[] AUTOMOBILE_COLUMNS = {KEY_AUTOMOBILE_TYPE, KEY_GAS_TYPE, KEY_GAS_PRICE, KEY_GAS_LITERS, KEY_AVG_KM, KEY_DATE}; // columns does not include KEY_ID

    //ACTIVITY FOOD TRACKER
    private static final String FOODTRACKER_TABLE_NAME = "FOODTRACKER_TABLE";
    private static final String KEY_FOODTRACKER_NAME = "NAME";
    private static final String KEY_FOODTRACKER_CALORIES = "CALORIES";
    private static final String KEY_FOODTRACKER_TOTAL_FAT = "TOTAL_FAT";
    private static final String KEY_FOODTRACKER_TOTAL_CARBS = "TOTAL_CARBS";
    private static final String[] FOODTRACKER_COLUMNS = {KEY_FOODTRACKER_NAME, KEY_FOODTRACKER_CALORIES, KEY_FOODTRACKER_TOTAL_FAT, KEY_FOODTRACKER_TOTAL_CARBS}; // columns does not include KEY_ID
    
   
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
        Log.i(ACTIVITY_NAME, "-- In Constructor");
        database = getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "-- In onCreate(), creating a database");
        
        // CREATE TABLE THERMOSTAT_TABLE (_ID INTEGER PK AUTO, DAY TEXT, HOUR TEXT, MINUTE TEXT, TEMPERATURE TEXT);
        db.execSQL("CREATE TABLE " + THERMOSTAT_TABLE_NAME
                + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_THERMOSTAT_DAY + " TEXT, "
                + KEY_THERMOSTAT_TIME + " TEXT, "
                + KEY_THERMOSTAT_TEMPERATURE + " TEXT"
                + " );");
      
        // CREATE TABLE ACTIVITY_TABLE (_ID INTEGER PK AUTO, DAY TEXT, TIME TEXT, MINUTE TEXT, ACTIVITY TEXT, NOTES TEXT);
        db.execSQL("CREATE TABLE " + ACTIVITY_TABLE_NAME
                + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ACTIVITY_DAY + " TEXT, "
                + KEY_ACTIVITY_TIME + " TEXT, "
                + KEY_ACTIVITY_ACTIVITY + " TEXT,"
                + KEY_ACTIVITY_NOTES + " TEXT"
                + " );");

        // CREATE TABLE AUTOMOBILE TABLE (_ID INTEGER PK AUTO, CAR TEXT, GAS TEXT, PRICE TEXT, LITERS TEXT, AVGKM TEXT, DATE TEXT);
        db.execSQL("CREATE TABLE " + AUTOMOBILE_TABLE_NAME
                + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_AUTOMOBILE_TYPE + " TEXT, "
                + KEY_GAS_TYPE + " TEXT, "
                + KEY_GAS_PRICE + " TEXT,"
                + KEY_GAS_LITERS + " TEXT,"
                + KEY_AVG_KM + " TEXT,"
                + KEY_DATE + " TEXT"
                + " );");
        
        // CREATE TABLE FOOD_TABLE (_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES TEXT, TOTAL_FAT TEXT, TOTAL_CARBS TEXT);
        db.execSQL("CREATE TABLE " + FOODTRACKER_TABLE_NAME
                + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_FOODTRACKER_NAME + " TEXT, "
                + KEY_FOODTRACKER_CALORIES + " TEXT, "
                + KEY_FOODTRACKER_TOTAL_FAT + " TEXT,"
                + KEY_FOODTRACKER_TOTAL_CARBS + " TEXT"
                + " );");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i(ACTIVITY_NAME, "-- In onUpgrade(), upgrading a database");
        
        // DROP TABLE IF EXISTS THERMOSTAT_TABLE
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AUTOMOBILE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOODTRACKER_TABLE_NAME);
        
        // recreate db using onCreate(db)
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i(ACTIVITY_NAME, "-- In onDowngrade(), downgrading a database");
        
        // DROP TABLE IF EXISTS THERMOSTAT_TABLE
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AUTOMOBILE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOODTRACKER_TABLE_NAME);
        
        // recreate db using onCreate(db)
        onCreate(db);
    }
    
    
    // -------------
    //  GET ITEM ID
    // -------------

    public int getThermostatItemID(int position) {
        Log.i(ACTIVITY_NAME, "-- In getThermostatItemID()");

        return getItemID(position, THERMOSTAT_TABLE_NAME);
    }

    public int getActivityItemID(int position) {
        Log.i(ACTIVITY_NAME, "-- In getActivityItemID()");

        return getItemID(position, ACTIVITY_TABLE_NAME);
    }

    public int getAutomobileItemID(int position) {
        Log.i(ACTIVITY_NAME, "-- In getAutomobileItemID()");

        return getItemID(position, AUTOMOBILE_TABLE_NAME);
    }
    
    public int getFoodTrackerItemID(int position) {
        Log.i(ACTIVITY_NAME, "-- In getFoodTrackerItemID()");
        
        return getItemID(position, FOODTRACKER_TABLE_NAME);
    }
    /**
     * Get id from database given position.
     * Removing entries from db will make id != position, so getItemID is needed
     * This is for internal use only. public get___ItemID will call this private method.
     *
     * @param position  Position of entry in database that you want the ID of
     * @param tableName Table that will be queried. Must be one of the class constants listed
     * @return id of database entry at given position
     */
    private int getItemID(int position, String tableName) {
        Log.i(ACTIVITY_NAME, "-- In getItemID()");
        
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);
        
        if (cursor.moveToPosition(position)) {
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
        }
        else {
            Log.i(ACTIVITY_NAME, "-- In getItemID(), no data in position " + position);
            return -1;
        }
    }
    
    
    // --------------------
    //  ADD DATABASE ENTRY
    // --------------------
    
    public void addThermostatDBEntry(ArrayList<String> dataToDB) {
        Log.i(ACTIVITY_NAME, "-- In addThermostatDBEntry()");
        
        addDBEntry(dataToDB, THERMOSTAT_TABLE_NAME, THERMOSTAT_COLUMNS);
    }

    public void addActivityDBEntry(ArrayList<String> dataToDB) {
        Log.i(ACTIVITY_NAME, "-- In addActivityDBEntry()");

        addDBEntry(dataToDB, ACTIVITY_TABLE_NAME, ACTIVITY_COLUMNS);
    }

    public void addAutomobileDBEntry(ArrayList<String> dataToDB) {
        Log.i(ACTIVITY_NAME, "-- In addAutomobileDBEntry()");

        addDBEntry(dataToDB, AUTOMOBILE_TABLE_NAME, AUTOMOBILE_COLUMNS);
    }
    
    public void addFoodTrackerDBEntry(ArrayList<String> dataToDB) {
        Log.i(ACTIVITY_NAME, "-- In addFoodTrackerDBEntry()");
        
        addDBEntry(dataToDB, FOODTRACKER_TABLE_NAME, FOODTRACKER_COLUMNS);
    }
    /**
     * Adds the ArrayList of Strings into the given database.
     * This is for internal use only. public add___DBEntry will call this private method.
     *
     * @param dataToDB  ArrayList of Strings that will be inserted into database
     * @param tableName Table that data will be inserted into
     * @param columns   Columns that data will be inserted into
     */
    private void addDBEntry(ArrayList<String> dataToDB, String tableName, String[] columns) {
        Log.i(ACTIVITY_NAME, "-- In addDBEntry()");
        
        ContentValues values = new ContentValues();
        
        for (int i = 0; i < columns.length; i++) {
            values.put(columns[i], dataToDB.get(i));
        }
        
        database.insertWithOnConflict(tableName, "NULL FIELD", values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    
    
    // -------------------
    //  GET DATABASE DATA
    // -------------------
    
    public ArrayList<ArrayList<String>> getThermostatDBData() {
        Log.i(ACTIVITY_NAME, "-- In getThermostatDBData()");
        
        return getDBData(THERMOSTAT_TABLE_NAME);
    }

    public ArrayList<ArrayList<String>> getActivityDBData() {
        Log.i(ACTIVITY_NAME, "-- In getActivityDBData()");

        return getDBData(ACTIVITY_TABLE_NAME);
    }

    public ArrayList<ArrayList<String>> getAutomobileDBData() {
        Log.i(ACTIVITY_NAME, "-- In getActivityDBData()");

        return getDBData(AUTOMOBILE_TABLE_NAME);
    }

    public ArrayList<ArrayList<String>> getFoodTrackerDBData() {
        Log.i(ACTIVITY_NAME, "-- In getActivityDBData()");
        
        return getDBData(FOODTRACKER_TABLE_NAME);
    }

    
    /**
     * Goes through the specified table in the database and returns a 2D ArrayList of the data.
     * This is for internal use only. public get___DBData will call this private method.
     *
     * @param tableName Table that will be queried. Must be one of the class constants listed
     * @return 2D ArrayList of Strings with all of the data from the given table
     */
    private ArrayList<ArrayList<String>> getDBData(String tableName) {
        Log.i(ACTIVITY_NAME, "-- In getDBData()");
        
        ArrayList<ArrayList<String>> dataFromDB = new ArrayList<>();
        
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);
        Log.i(ACTIVITY_NAME, "-- In getDBData(), queried database successfully. # of rows: " + cursor.getCount());
        if (cursor.moveToFirst()) {
            for (int row = 0; row < cursor.getCount(); row++) {
                
                dataFromDB.add(new ArrayList<String>());
                
                // add all the values of each column to ArrayList that will be returned
                for (int col = 0; col < cursor.getColumnCount(); col++) {
                    String cellRetrieved = cursor.getString(col);
                    //Log.i(ACTIVITY_NAME, "-- -- Got: " + cellRetrieved);
                    dataFromDB.get(row).add(cellRetrieved);
                }
                
                cursor.moveToNext();
            }
        }
    
        cursor.close();
        return dataFromDB;
    }
    
    
    // -----------------------
    //  DELETE DATABASE ENTRY
    // -----------------------
    
    public void deleteThermostatDBEntry(int ID) {
        Log.i(ACTIVITY_NAME, "-- In deleteThermostatDBEntry()");
        
        deleteDBEntry(ID, THERMOSTAT_TABLE_NAME);
    }

    public void deleteActivityDBEntry(int ID) {
        Log.i(ACTIVITY_NAME, "-- In deleteActivityDBEntry()");

        deleteDBEntry(ID, ACTIVITY_TABLE_NAME);
    }

    public void deleteAutomobileDBEntry(int ID) {
        Log.i(ACTIVITY_NAME, "-- In deleteActivityDBEntry()");

        deleteDBEntry(ID, AUTOMOBILE_TABLE_NAME);
    }
    
    public void deleteFoodTrackerDBEntry(int ID) {
        Log.i(ACTIVITY_NAME, "-- In deleteActivityDBEntry()");
        
        deleteDBEntry(ID, FOODTRACKER_TABLE_NAME);
    }
    
    /**
     * Deletes the row associated to the given ID.
     * This is for internal use only. public delete___DBEntry will call this private method.
     *
     * @param ID        Primary Key ID that will be deleted from table
     * @param tableName Table that will be queried. Must be one of the class constants listed
     */
    private void deleteDBEntry(int ID, String tableName) {
        Log.i(ACTIVITY_NAME, "-- In deleteDBEntry()");
        
        //database.execSQL("DELETE FROM " + tableName + " WHERE " + KEY_ID + " = " + ID);
        database.delete(tableName, KEY_ID + " = " + ID, null);
    }
    
    
    // -----------------------
    //  UPDATE DATABASE ENTRY
    // -----------------------
    
    public void updateThermostatDBEntry(int ID, ArrayList<String> newData) {
        Log.i(ACTIVITY_NAME, "-- In updateThermostatDBEntry()");
        
        updateDBEntry(ID, newData, THERMOSTAT_TABLE_NAME, THERMOSTAT_COLUMNS);
    }

    public void updateActivityDBEntry(int ID, ArrayList<String> newData) {
        Log.i(ACTIVITY_NAME, "-- In updateActivityDBEntry()");

        updateDBEntry(ID, newData, ACTIVITY_TABLE_NAME, ACTIVITY_COLUMNS);
    }

    public void updateAutomobileDBEntry(int ID, ArrayList<String> newData) {
        Log.i(ACTIVITY_NAME, "-- In updateActivityDBEntry()");

        updateDBEntry(ID, newData, AUTOMOBILE_TABLE_NAME, AUTOMOBILE_COLUMNS);
    }

    public void updateFoodTrackerDBEntry(int ID, ArrayList<String> newData) {
        Log.i(ACTIVITY_NAME, "-- In updateActivityDBEntry()");
        
        updateDBEntry(ID, newData, FOODTRACKER_TABLE_NAME, FOODTRACKER_COLUMNS);
        
    }

    
    /**
     * Updates the row with the given ID with the ArrayList of new data
     * This is for internal use only. public update___DBEntry will call this private method.
     *
     * @param ID        Primary Key ID associated to row being updated.
     * @param newData   ArrayList of Strings of new entries that will replace old row
     * @param tableName Table that will be queried. Must be one of the class constants listed
     * @param columns   Columns that data will be replaced
     */
    private void updateDBEntry(int ID, ArrayList<String> newData, String tableName, String[] columns) {
        Log.i(ACTIVITY_NAME, "-- In updateDBEntry()");
        
        ContentValues values = new ContentValues();
        
        for (int i = 0; i < columns.length; i++) {
            values.put(columns[i], newData.get(i));
        }
        
        database.updateWithOnConflict(tableName, values, KEY_ID + " = " + ID, null, SQLiteDatabase.CONFLICT_IGNORE);
    }
}




