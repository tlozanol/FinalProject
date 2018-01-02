package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FoodTrackerHelp extends Activity {
    private static final String ACTIVITY_NAME = "FoodTrackerHelp";
    Button btnBackFoodTracker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_help);
        
        btnBackFoodTracker = findViewById(R.id.FTbtnBackToFoodTracker);
        btnBackFoodTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        
    }
}



