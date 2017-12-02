package team.best.team.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThermostatActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatActivity";
    
    ArrayList<ArrayList<String>> thermostatArray = new ArrayList<>();
    
    DatabaseHelper databaseHelper;
    ListView listThermostat;
    ThermostatListAdapter thermostatListAdapter;
    SQLiteDatabase writableDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
    
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        thermostatArray = databaseHelper.getThermostatDataFromDB();
    
        listThermostat = findViewById(R.id.listThermostat);
        thermostatListAdapter = new ThermostatListAdapter(this);
        listThermostat.setAdapter(thermostatListAdapter);
    
        listThermostat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long position) {
                long clickedItemID = databaseHelper.getThermostatItemID((int) position);
                Log.i(ACTIVITY_NAME, "-- Past getItemID 1");
                String clickedDay = thermostatArray.get((int) position).get(0);
                String clickedTime = thermostatArray.get((int) position).get(1);
                String clickedTemperature = thermostatArray.get((int) position).get(2);
            
                Log.i(ACTIVITY_NAME, "-- Past getItemID 2");
            
                Bundle thermostatEntryBundle = new Bundle();
                thermostatEntryBundle.putLong("ID", clickedItemID);
                thermostatEntryBundle.putString("Day", clickedDay);
                thermostatEntryBundle.putString("Time", clickedTime);
                thermostatEntryBundle.putString("Temperature", clickedTemperature);
            
                Log.i(ACTIVITY_NAME, "-- Past getItemID 3");
            
                Intent goToThermostatEdit = new Intent(ThermostatActivity.this, ThermostatEditActivity.class);
                goToThermostatEdit.putExtras(thermostatEntryBundle);
                Log.i(ACTIVITY_NAME, "-- Past getItemID 4");
                startActivityForResult(goToThermostatEdit, 1); // TODO forResult()
            }
        });
        
        Button buttonAddTemperature = findViewById(R.id.buttonAddTemperature);
    
        buttonAddTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToThermostatAdd = new Intent(ThermostatActivity.this, ThermostatAddActivity.class);
                startActivity(goToThermostatAdd); // TODO forResult()
                
                /*ArrayList<String> dataToDB = new ArrayList<>();
                dataToDB.add("Friday");
                dataToDB.add("6:00");
                dataToDB.add("20");
                databaseHelper.addThermostatDataToDB(dataToDB);*/
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "-- In onResume()");
        
        refreshThermostatEntries();
    }
    
    private void refreshThermostatEntries() {
        Log.i(ACTIVITY_NAME, "-- In refreshThermostatEntries()");
        
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        thermostatArray = databaseHelper.getThermostatDataFromDB();
        
        thermostatListAdapter = new ThermostatListAdapter(this);
        listThermostat.setAdapter(thermostatListAdapter);
    }
    
    
    private class ThermostatListAdapter extends ArrayAdapter<String> {
        
        public ThermostatListAdapter(Context context) {
            super(context, 0);
            Log.i(ACTIVITY_NAME, "-- ThermostatListAdapter constructor");
        }
        
        @Override
        public int getCount() {
            return thermostatArray.size();
        }
        
        /*public String getItem(int position) {
            return thermostatArray.get(position);
        }*/
        
        /*public ArrayList<String> getItems(int position)
        {
            ArrayList<String> row = new ArrayList<>();
            for (int i = 0; i < thermostatArray.get(position).size(); i++){
                row.add(thermostatArray.get(position).get(i));
            }
            return row;
        }*/
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(ACTIVITY_NAME, "-- ThermostatListAdapter getView()");
            LayoutInflater inflater = ThermostatActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.thermostat_entry, null);
            
            TextView textThermostatEntryID = result.findViewById(R.id.textThermostatEntryID);
            TextView textThermostatEntryDay = result.findViewById(R.id.textThermostatEntryDay);
            TextView textThermostatEntryTime = result.findViewById(R.id.textThermostatEntryTime);
            TextView textThermostatEntryTemperature = result.findViewById(R.id.textThermostatEntryTemperature);
            
            // getItem(s) unnecessary since we are parsing through the ArrayList in getView
            ArrayList<String> row = thermostatArray.get(position);
            
            textThermostatEntryID.setText(row.get(0));
            textThermostatEntryDay.setText(row.get(1));
            textThermostatEntryTime.setText(row.get(2));
            textThermostatEntryTemperature.setText(row.get(3));
            
            return result;
        }
    }
}




