package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThermostatEditActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatEditActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_thermostat_entry);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        Bundle thermostatEntryBundle = getIntent().getExtras();
        
        Button buttonCancelThermostatEntry = findViewById(R.id.buttonCancelThermostatEntry);
        Button buttonSaveThermostatEntry = findViewById(R.id.buttonSaveThermostatEntry);
        Button buttonDeleteThermostatEntry = findViewById(R.id.buttonDeleteThermostatEntry);
        
        buttonCancelThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        buttonSaveThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        buttonDeleteThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
    }
}




