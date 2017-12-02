package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThermostatAddActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatAddActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_thermostat_entry);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        Button buttonCancelThermostatEntry = findViewById(R.id.buttonCancelThermostatEntry);
        Button buttonSaveThermostatEntry = findViewById(R.id.buttonSaveThermostatEntry);
        
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
    }
}




