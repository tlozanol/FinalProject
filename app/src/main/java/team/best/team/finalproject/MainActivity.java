package team.best.team.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    
    private static final String ACTIVITY_NAME = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "-- In onCreate");
    
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(ACTIVITY_NAME, "-- In onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.i(ACTIVITY_NAME, "-- In onOptionsItemSelected");
        
        switch (menuItem.getItemId()) {
            case R.id.action_food:
                Log.i(ACTIVITY_NAME, "-- -- selected Food");
                Intent goToFoodTrackerSplashScreen = new Intent(MainActivity.this, FoodTrackerActivity.class);
                startActivity(goToFoodTrackerSplashScreen);
                break;
            case R.id.action_activity:
                Log.i(ACTIVITY_NAME, "-- -- selected Activity Tracker");
                Intent goToActivityTracker = new Intent(MainActivity.this, ActivityTrackerActivity.class);
                startActivity(goToActivityTracker);
                break;
            case R.id.action_thermostat:
                Log.i(ACTIVITY_NAME, "-- -- selected Thermostat");
                Intent goToThermostat = new Intent(MainActivity.this, ThermostatActivity.class);
                startActivity(goToThermostat);
                break;
            case R.id.action_automobile:
                Log.i(ACTIVITY_NAME, "-- -- selected Automobile");
                Intent goToAutomobile = new Intent(MainActivity.this, AutomobileActivity.class);
                startActivity(goToAutomobile);
                break;
        }
        return true;
    }
}




