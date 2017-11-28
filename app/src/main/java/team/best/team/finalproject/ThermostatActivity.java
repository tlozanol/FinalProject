package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ThermostatActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatActivity";
    
    DatabaseHelper databaseHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
    
        databaseHelper = new DatabaseHelper(this);
    
        Button buttonAddTemperature = findViewById(R.id.buttonAddTemperature);
        Button buttonGetTemperature = findViewById(R.id.buttonGetTemperature);
    
        buttonAddTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> dataToDB = new ArrayList<>();
                dataToDB.add("Monday");
                dataToDB.add("2:00");
                dataToDB.add("20");
                databaseHelper.addThermostatDataToDB(dataToDB);
            }
        });
    
        buttonGetTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ArrayList<String>> dataFromDB = databaseHelper.getThermostatDataFromDB();
                //Log.i(ACTIVITY_NAME, "-- got: " + dataFromDB.get(0).get(0));
            }
        });
    }
    
}




