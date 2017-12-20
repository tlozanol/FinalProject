package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThermostatAbout extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatAbout";
    
    Button buttonBackThermostat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat_about);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        buttonBackThermostat = findViewById(R.id.buttonBackThermostat);
        buttonBackThermostat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
    }
}
