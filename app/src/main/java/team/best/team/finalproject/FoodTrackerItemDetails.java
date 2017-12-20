package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class FoodTrackerItemDetails extends Activity {
    
    protected static final String ACTIVITY_NAME = "FoodTrackerItemDetails";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_item_details);
        
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        //Bundle info = getIntent().getExtras();
        
        
        FoodTrackerItemFragment frag = new FoodTrackerItemFragment();
        //forward the bundle to the fragment
        //frag.setArguments(info);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.frameLayout, frag);
        fragTrans.commit();
        
    }
    
    
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_or_edit_thermostat_entry);
//        Log.i(ACTIVITY_NAME, "-- In onCreate()");
//
//        Bundle thermostatEntryBundle = getIntent().getExtras();
//
//        ThermostatInputFragment thermostatFrag = new ThermostatInputFragment();
//        thermostatFrag.setParent(ThermostatAddOrEditActivity.this); // sets parent so this can be used by frag
//        thermostatFrag.setArguments(thermostatEntryBundle);
//        FragmentManager thermostatFragManager = getFragmentManager();
//        FragmentTransaction thermostatFragTransaction = thermostatFragManager.beginTransaction();
//        thermostatFragTransaction.replace(R.id.frameLayout, thermostatFrag);
//        thermostatFragTransaction.commit();
//    }
//}