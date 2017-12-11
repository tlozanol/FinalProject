package team.best.team.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThermostatActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatActivity";
    
    private final int THERMOSTAT_REQUEST_ADD = 10;
    private final int THERMOSTAT_REQUEST_EDIT = 20;
    private final int THERMOSTAT_RESULT_SAVE = 100; // cancel not included, since it acts as Back button
    private final int THERMOSTAT_RESULT_DELETE = 200;
    
    ArrayList<ArrayList<String>> thermostatArray = new ArrayList<>();
    ArrayList<String> lastDeletedEntry = new ArrayList<>();
    private int lastPositionClicked;
    
    DatabaseHelper databaseHelper;
    ListView listThermostat;
    ThermostatListAdapter thermostatListAdapter;
    SQLiteDatabase writableDatabase;
    RelativeLayout thermostatRelativeLayout;
    
    Button buttonAddTemperature;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
    
        thermostatRelativeLayout = findViewById(R.id.thermostatRelativeLayout);
        
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        thermostatArray = databaseHelper.getThermostatDBData();
    
        if (thermostatArray.size() == 0)
            showCustomDialog("No entries found. Add one now?", "Yes", "No");
        
        listThermostat = findViewById(R.id.listThermostat);
        thermostatListAdapter = new ThermostatListAdapter(this);
        listThermostat.setAdapter(thermostatListAdapter);
    
        listThermostat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long position) {
                lastPositionClicked = (int) position;
                Bundle thermostatEntryBundle = createThermostatEditBundle((int) position);
                Intent goToThermostatEdit = new Intent(ThermostatActivity.this, ThermostatAddOrEditActivity.class);
                goToThermostatEdit.putExtras(thermostatEntryBundle);
                startActivityForResult(goToThermostatEdit, THERMOSTAT_REQUEST_EDIT);
            }
        });
    
        buttonAddTemperature = findViewById(R.id.buttonAddTemperature);
        
        buttonAddTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle thermostatEntryBundle = createThermostatAddBundle();
                Intent goToThermostatAdd = new Intent(ThermostatActivity.this, ThermostatAddOrEditActivity.class);
                goToThermostatAdd.putExtras(thermostatEntryBundle);
                startActivityForResult(goToThermostatAdd, THERMOSTAT_REQUEST_ADD);
            }
        });
    }
    
    private Bundle createThermostatAddBundle() {
        Bundle thermostatEntryBundle = new Bundle();
        thermostatEntryBundle.putBoolean("InAdd", true);
        thermostatEntryBundle.putInt("ID", -1);
        thermostatEntryBundle.putString("Day", " ");
        thermostatEntryBundle.putString("Time", "");
        thermostatEntryBundle.putString("Temperature", "");
        
        return thermostatEntryBundle;
    }
    
    private Bundle createThermostatEditBundle(int position) {
        int clickedItemID = databaseHelper.getThermostatItemID(position);
        String clickedDay = thermostatArray.get(position).get(1);
        String clickedTime = thermostatArray.get(position).get(2);
        String clickedTemperature = thermostatArray.get(position).get(3);
        
        Bundle thermostatEntryBundle = new Bundle();
        thermostatEntryBundle.putBoolean("InAdd", false);
        thermostatEntryBundle.putInt("ID", clickedItemID);
        thermostatEntryBundle.putString("Day", clickedDay);
        thermostatEntryBundle.putString("Time", clickedTime);
        thermostatEntryBundle.putString("Temperature", clickedTemperature);
        
        return thermostatEntryBundle;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "-- In onResume()");
        
        refreshThermostatEntries();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(ACTIVITY_NAME, "-- In onActivityResult()");
    
        if (requestCode == THERMOSTAT_REQUEST_ADD) {
            if (resultCode == THERMOSTAT_RESULT_SAVE) {
                // Save in Add
                Log.i(ACTIVITY_NAME, "-- -- Returned from Add: Save");
                showToast("Entry added!");
                
                String newDay = data.getStringExtra("Day");
                String newTime = data.getStringExtra("Time");
                String newTemperature = data.getStringExtra("Temperature");
    
                ArrayList<String> dataToDB = new ArrayList<>();
                dataToDB.add(newDay);
                dataToDB.add(newTime);
                dataToDB.add(newTemperature);
    
                databaseHelper.addThermostatDBEntry(dataToDB);
            }
            // Delete is not possible in Add
            // Cancel acts as a Back button, so do nothing
        }
        else if (requestCode == THERMOSTAT_REQUEST_EDIT) {
            if (resultCode == THERMOSTAT_RESULT_SAVE) {
                // Save in Edit
                Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Save");
                showToast("Entry saved!");
                
                int IDToUpdate = data.getIntExtra("ID", 1);
                String newDay = data.getStringExtra("Day");
                String newTime = data.getStringExtra("Time");
                String newTemperature = data.getStringExtra("Temperature");
    
                ArrayList<String> dataToUpdateInDB = new ArrayList<>();
                dataToUpdateInDB.add(newDay);
                dataToUpdateInDB.add(newTime);
                dataToUpdateInDB.add(newTemperature);
    
                databaseHelper.updateThermostatDBEntry(IDToUpdate, dataToUpdateInDB);
            }
            else if (resultCode == THERMOSTAT_RESULT_DELETE) {
                // Delete in Edit
                Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Delete");
    
                int IDToDelete = data.getIntExtra("ID", 0);
    
                lastDeletedEntry = thermostatArray.get(lastPositionClicked);
                lastDeletedEntry.remove(0); // removes id from ArrayList, since adding needs no id
                showSnackBar("Entry deleted!", "UNDO");
                
                databaseHelper.deleteThermostatDBEntry(IDToDelete);
            }
            // Cancel acts as a Back button, so do nothing
        }
    }
    
    private void undoDeleteThermostatEntry() {
        Log.i(ACTIVITY_NAME, "-- In undoDeleteThermostatEntry()");
        thermostatArray.add(lastPositionClicked, lastDeletedEntry);
        databaseHelper.addThermostatDBEntry(lastDeletedEntry);
        refreshThermostatEntries();
    }
    
    private void refreshThermostatEntries() {
        Log.i(ACTIVITY_NAME, "-- In refreshThermostatEntries()");
        
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        thermostatArray = databaseHelper.getThermostatDBData();
        
        thermostatListAdapter = new ThermostatListAdapter(this);
        listThermostat.setAdapter(thermostatListAdapter);
    }
    
    private void showToast(String messageText) {
        Log.i(ACTIVITY_NAME, "-- In showToast()");
        Toast toast = Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG);
        toast.show();
    }
    
    private void showSnackBar(String messageText) {
        Log.i(ACTIVITY_NAME, "-- In showSnackBar()");
        showSnackBar(messageText, null);
    }
    
    private void showSnackBar(String messageText, String buttonText) {
        // reference on how to use Snackbars: https://www.journaldev.com/10324/android-snackbar-example-tutorial
        Log.i(ACTIVITY_NAME, "-- In showSnackBar()");
        final Snackbar snackbar = Snackbar.make(thermostatRelativeLayout, messageText, Snackbar.LENGTH_LONG);
        snackbar.show();
        
        snackbar.setAction(buttonText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoDeleteThermostatEntry();
                showSnackBar("Undid delete!");
            }
        });
    }
    
    private void showCustomDialog(String messageText, String buttonPositiveText, String buttonNegativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThermostatActivity.this);
        builder.setMessage(messageText);
        builder.setPositiveButton(buttonPositiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonAddTemperature.callOnClick();
            }
        });
        builder.setNegativeButton(buttonNegativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing, just close dialog box.
            }
        });
        builder.create().show();
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
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.i(ACTIVITY_NAME, "-- ThermostatListAdapter getView()");
            LayoutInflater inflater = ThermostatActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.thermostat_entry, null);
            
            TextView textThermostatEntryDay = result.findViewById(R.id.textThermostatEntryDay);
            TextView textThermostatEntryTime = result.findViewById(R.id.textThermostatEntryTime);
            TextView textThermostatEntryTemperature = result.findViewById(R.id.textThermostatEntryTemperature);
            
            // getItem(s) unnecessary since we are parsing through the ArrayList in getView
            ArrayList<String> row = thermostatArray.get(position);
            
            textThermostatEntryDay.setText(row.get(1));
            textThermostatEntryTime.setText(row.get(2));
            textThermostatEntryTemperature.setText(row.get(3));
            
            return result;
        }
    }
}




