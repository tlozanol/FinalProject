package team.best.team.finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ThermostatInputFragment extends Fragment {
    
    private static final String ACTIVITY_NAME = "ThermostatInputFragment";
    
    private final int THERMOSTAT_RESULT_SAVE = 100; // cancel not included, since it acts as Back button
    private final int THERMOSTAT_RESULT_DELETE = 200; // TODO make originals public static, access from ThermoAct
    
    private ThermostatAddOrEditActivity thermostatAddOrEditActivity; // parent class. used to setResult and finish
    
    private int clickedItemID;
    private EditText editTextThermostatDay;
    private EditText editTextThermostatTime;
    private EditText editTextThermostatTemperature;
    
    public ThermostatInputFragment() {
        Log.i(ACTIVITY_NAME, "-- In constructor");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "-- In onCreate");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "-- In onCreateView");
        
        View view = inflater.inflate(R.layout.thermostat_input, null);
        
        Bundle thermostatEntryBundle = getArguments();
        boolean inAdd = thermostatEntryBundle.getBoolean("InAdd", false);
        clickedItemID = thermostatEntryBundle.getInt("ID", -1);
        String clickedDay = thermostatEntryBundle.getString("Day", " ");
        String clickedTime = thermostatEntryBundle.getString("Time", " ");
        String clickedTemperature = thermostatEntryBundle.getString("Temperature", " ");
    
        // TODO set day EditText to selection (Picker) of weekdays
        editTextThermostatDay = view.findViewById(R.id.editTextThermostatDay);
        editTextThermostatDay.setText(clickedDay);
    
        editTextThermostatTime = view.findViewById(R.id.editTextThermostatTime);
        editTextThermostatTime.setText(clickedTime);
    
        editTextThermostatTemperature = view.findViewById(R.id.editTextThermostatTemperature);
        editTextThermostatTemperature.setText(clickedTemperature);
        
        Button buttonCancelThermostatEntry = view.findViewById(R.id.buttonCancelThermostatEntry);
        buttonCancelThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Cancel Clicked");
                thermostatAddOrEditActivity.setResult(0); // unused. reset to 0
                thermostatAddOrEditActivity.finish();
            }
        });
    
        Button buttonSaveThermostatEntry = view.findViewById(R.id.buttonSaveThermostatEntry);
        buttonSaveThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Save Clicked");
                Intent saveIntent = createThermostatInputIntent();
                thermostatAddOrEditActivity.setResult(THERMOSTAT_RESULT_SAVE, saveIntent);
                thermostatAddOrEditActivity.finish();
            }
        });
    
        Button buttonDeleteThermostatEntry = view.findViewById(R.id.buttonDeleteThermostatEntry);
        buttonDeleteThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Delete Clicked");
                Intent deleteIntent = createThermostatInputIntent();
                thermostatAddOrEditActivity.setResult(THERMOSTAT_RESULT_DELETE, deleteIntent);
                thermostatAddOrEditActivity.finish();
            }
        });
    
        TextView addOrEditTitle = view.findViewById(R.id.textThermostatAddOrEditTitle);
        if (inAdd) {
            // Edit and Add share the same Activity
            // switch the title to Add Entry and hide the Delete Button
            addOrEditTitle.setText(R.string.thermostatAddTitle);
            buttonDeleteThermostatEntry.setVisibility(View.INVISIBLE);
        }
        
        return view;
    }
    
    public void setParent(ThermostatAddOrEditActivity parent) {
        thermostatAddOrEditActivity = parent;
    }
    
    private Intent createThermostatInputIntent() {
        // reference on how to send data with result: https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
        Intent resultIntent = new Intent();
        
        resultIntent.putExtra("ID", clickedItemID);
        resultIntent.putExtra("Day", editTextThermostatDay.getText().toString());
        resultIntent.putExtra("Time", editTextThermostatTime.getText().toString());
        resultIntent.putExtra("Temperature", editTextThermostatTemperature.getText().toString());
        
        return resultIntent;
    }
    
}




