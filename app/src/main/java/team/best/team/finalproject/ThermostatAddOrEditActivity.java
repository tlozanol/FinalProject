package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThermostatAddOrEditActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatAddOrEditA";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit_thermostat_entry);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        Bundle thermostatEntryBundle = getIntent().getExtras();
    
        ThermostatInputFragment thermostatFrag = new ThermostatInputFragment();
        thermostatFrag.setParent(ThermostatAddOrEditActivity.this); // sets parent so this can be used by frag
        thermostatFrag.setArguments(thermostatEntryBundle);
        FragmentManager thermostatFragManager = getFragmentManager();
        FragmentTransaction thermostatFragTransaction = thermostatFragManager.beginTransaction();
        thermostatFragTransaction.replace(R.id.frameLayout, thermostatFrag);
        thermostatFragTransaction.commit();
    }
}




