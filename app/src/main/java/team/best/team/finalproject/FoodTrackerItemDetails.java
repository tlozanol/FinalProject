package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FoodTrackerItemDetails extends Activity {
    
    protected static final String ACTIVITY_NAME = "FoodTrackerItemDetails";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_item_details);
        
        Log.i(ACTIVITY_NAME, "-- In onCreate() before launching fragment");
        Bundle foodtrackerEntryBundle = getIntent().getExtras();
        FoodTrackerItemFragment foodtrackerFrag = new FoodTrackerItemFragment();
        foodtrackerFrag.setParent(FoodTrackerItemDetails.this);
        foodtrackerFrag.setArguments(foodtrackerEntryBundle);
        
        
        FragmentManager foodtrackerfragMan = getFragmentManager();
        FragmentTransaction foodtrackerfragTrans = foodtrackerfragMan.beginTransaction();
        foodtrackerfragTrans.replace(R.id.frameLayout, foodtrackerFrag);
        foodtrackerfragTrans.commit();
        
        
    }
}
