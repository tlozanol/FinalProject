package team.best.team.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ThermostatInputFragment extends Fragment {
    
    private static final String ACTIVITY_NAME = "ThermostatInputFragment";
    
    ThermostatAddOrEditActivity thermostatAddOrEditActivity; // parent class. used to setResult and finish
    
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
        
        //long clickedItemID = thermostatEntryBundle.getLong("ID", -1);
        String clickedDay = thermostatEntryBundle.getString("Day", " ");
        String clickedTime = thermostatEntryBundle.getString("Time", " ");
        String clickedTemperature = thermostatEntryBundle.getString("Temperature", " ");
        
        EditText editTextThermostatDay = view.findViewById(R.id.editTextThermostatDay);
        if (editTextThermostatDay == null)
            Log.i(ACTIVITY_NAME, "-- editTextThermostatDay not found");
        else
            editTextThermostatDay.setText(clickedDay);
        
        EditText editTextThermostatTime = view.findViewById(R.id.editTextThermostatTime);
        if (editTextThermostatTime == null)
            Log.i(ACTIVITY_NAME, "-- editTextThermostatTime not found");
        else
            editTextThermostatTime.setText(clickedTime);
        
        EditText editTextThermostatTemperature = view.findViewById(R.id.editTextThermostatTemperature);
        if (editTextThermostatTemperature == null)
            Log.i(ACTIVITY_NAME, "-- editTextThermostatTemperature not found");
        else
            editTextThermostatTemperature.setText(clickedTemperature);
        
        Button buttonCancelThermostatEntry = view.findViewById(R.id.buttonCancelThermostatEntry);
        Button buttonSaveThermostatEntry = view.findViewById(R.id.buttonSaveThermostatEntry);
        Button buttonDeleteThermostatEntry = view.findViewById(R.id.buttonDeleteThermostatEntry);
        
        // TODO set proper results below. may need to also send back ID somehow
        buttonCancelThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thermostatAddOrEditActivity.setResult(1);
                thermostatAddOrEditActivity.finish();
            }
        });
        
        buttonSaveThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thermostatAddOrEditActivity.setResult(2);
                thermostatAddOrEditActivity.finish();
            }
        });
        
        buttonDeleteThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thermostatAddOrEditActivity.setResult(3);
                thermostatAddOrEditActivity.finish();
            }
        });
        
        // TODO check if in Add. set Title, hide Delete button
        
        return view;
    }
    
    public void setParent(ThermostatAddOrEditActivity parent) {
        thermostatAddOrEditActivity = parent;
    }
    
}




