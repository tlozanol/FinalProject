package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Button buttonToFoodTracker = findViewById(R.id.buttonToFoodTracker);
        Button buttonToActivityTracker = findViewById(R.id.buttonToActivityTracker);
        Button buttonToThermostat = findViewById(R.id.buttonToThermostat);
        Button buttonToAutomobile = findViewById(R.id.buttonToAutomobile);
    
        buttonToFoodTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFoodTracker = new Intent(MainActivity.this, FoodTrackerActivity.class);
                startActivity(goToFoodTracker);
            }
        });
    
        buttonToActivityTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToActivityTracker = new Intent(MainActivity.this, ActivityTrackerActivity.class);
                startActivity(goToActivityTracker);
            }
        });
    
        buttonToThermostat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToThermostat = new Intent(MainActivity.this, ThermostatActivity.class);
                startActivity(goToThermostat);
            }
        });
    
        buttonToAutomobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAutomobile = new Intent(MainActivity.this, AutomobileActivity.class);
                startActivity(goToAutomobile);
            }
        });
    }
}




