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
    private static final int VERSION_NUM = 2;
    
    private static final String KEY_ID = "_ID"; // _ID is used by all tables
    
    private static final String THERMOSTAT_TABLE_NAME = "THERMOSTAT_TABLE";
    private static final String KEY_THERMOSTAT_DAY = "DAY";
    private static final String KEY_THERMOSTAT_TIME = "HOUR";
    private static final String KEY_THERMOSTAT_TEMPERATURE = "TEMPERATURE";
    private static final String[] THERMOSTAT_COLUMNS = {KEY_THERMOSTAT_DAY, KEY_THERMOSTAT_TIME, KEY_THERMOSTAT_TEMPERATURE}; // columns does not include KEY_ID
    
    
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
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i(ACTIVITY_NAME, "-- In onUpgrade(), upgrading a database");
        
        // DROP TABLE IF EXISTS THERMOSTAT_TABLE
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        
        // recreate db using onCreate(db)
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i(ACTIVITY_NAME, "-- In onDowngrade(), downgrading a database");
        
        // DROP TABLE IF EXISTS THERMOSTAT_TABLE
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        
        // recreate db using onCreate(db)
        onCreate(db);
    }
    
    
    // -----------------
    //  ADD TO DATABASE
    // -----------------
    
    public void addThermostatDataToDB(ArrayList<String> dataToDB) {
        Log.i(ACTIVITY_NAME, "-- In addThermostatDataToDB()");
        
        addDataToDB(dataToDB, THERMOSTAT_TABLE_NAME, THERMOSTAT_COLUMNS);
    }
    
    /**
     * Adds the ArrayList of Strings into the given database.
     * This is for internal use only. public add___DataToDB's will call this private method.
     *
     * @param dataToDB  ArrayList of Strings that will be inserted into database
     * @param tableName Table that data will be inserted into
     * @param columns   Columns that data will be inserted into
     */
    private void addDataToDB(ArrayList<String> dataToDB, String tableName, String[] columns) {
        Log.i(ACTIVITY_NAME, "-- In addDataToDB()");
        
        ContentValues values = new ContentValues();
        
        for (int i = 0; i < columns.length; i++) {
            values.put(columns[i], dataToDB.get(i));
            Log.i(ACTIVITY_NAME, "-- -- Added: " + dataToDB.get(i));
        }
        
        database.insertWithOnConflict(tableName, "NULL FIELD", values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    
    
    // -------------------
    //  GET FROM DATABASE
    // -------------------
    
    public ArrayList<ArrayList<String>> getThermostatDataFromDB() {
        Log.i(ACTIVITY_NAME, "-- In getThermostatDataFromDB()");
        
        return getDataFromTable(THERMOSTAT_TABLE_NAME, THERMOSTAT_COLUMNS);
    }
    
    /**
     * Goes through the specified table in the database and returns all the data.
     * This is for internal use only. public get___DataFromDB's will call this private method.
     *
     * @param tableName Table that will be queried. Must be one of the class constants listed
     * @param columns   Columns that will be queried from the given table
     * @return 2D ArrayList of Strings with all of the data from the given columns of the given table
     */
    private ArrayList<ArrayList<String>> getDataFromTable(String tableName, String[] columns) {
        Log.i(ACTIVITY_NAME, "-- In getDataFromTable()");
        
        ArrayList<ArrayList<String>> dataFromDB = new ArrayList<>();
        
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName, null);
        //Cursor cursor = database.query(tableName, columns, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "-- In getDataFromTable(), queried database successfully. # of rows: " + cursor.getCount());
        if (cursor.moveToFirst()) {
            for (int row = 0; row < cursor.getCount(); row++) {
                
                dataFromDB.add(new ArrayList<String>());
                
                // add all the values of each column to ArrayList that will be returned
                for (int col = 0; col < cursor.getColumnCount(); col++) {
                    String cellRetrieved = cursor.getString(col);
                    Log.i(ACTIVITY_NAME, "-- -- Got: " + cellRetrieved);
                    dataFromDB.get(row).add(cellRetrieved);
                }
                
                cursor.moveToNext();
            }
        }
        
        return dataFromDB;
    }
    
    
    // ----------------------
    //  REMOVE FROM DATABASE
    // ----------------------
    
    
}




