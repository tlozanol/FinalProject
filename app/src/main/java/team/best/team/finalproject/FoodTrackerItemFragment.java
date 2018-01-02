package team.best.team.finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class FoodTrackerItemFragment extends Fragment {
    
    //private Button FTbtnQuit;
    
    protected static final String ACTIVITY_NAME = "FoodTrackerItemFragment";
    private final int FOODTRACKER_RESULT_SAVE = 100;
    private final int FOODTRACKER_RESULT_DELETE = 200;
    private FoodTrackerActivity foodtrackerActivity;
    private FoodTrackerItemDetails foodTrackerItemDetails;
    
    private int clickedItemID;
    private EditText nameItem;
    private EditText calValue;
    private EditText fatValue;
    private EditText carbsValue;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "-- In onCreate");
        
        //setContentView(R.layout.activity_food_tracker_item_fragment);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(ACTIVITY_NAME, "-- In onCreateView");
        
        View view = inflater.inflate(R.layout.activity_food_tracker_item_fragment, null);
        Log.i(ACTIVITY_NAME, "-- In onCreateView, after view is created");
        Bundle foodtrackerEntryBundle = getArguments();
        boolean inAdd = foodtrackerEntryBundle.getBoolean("InAdd", false);
        clickedItemID = foodtrackerEntryBundle.getInt("ID", -1);
        String clickedName = foodtrackerEntryBundle.getString("Name", "");
        String clickedCal = foodtrackerEntryBundle.getString("Cal", "");
        String clickedFat = foodtrackerEntryBundle.getString("Fat", "");
        String clickedCarbs = foodtrackerEntryBundle.getString("Carbs", "");
        
        
        nameItem = view.findViewById(R.id.FTeditTxtItemName);
        nameItem.setText(clickedName);
        
        calValue = view.findViewById(R.id.FTeditTxtCalories);
        calValue.setText(clickedCal);
        
        fatValue = view.findViewById(R.id.FTeditTxtTotalFat);
        fatValue.setText(clickedFat);
        
        carbsValue = view.findViewById(R.id.FTeditTxtTotalCarbs);
        carbsValue.setText(clickedCarbs);
        
        //// SAVE BUTTON
        Log.i(ACTIVITY_NAME, "-- In onCreate, before click SAVE");
        Button btnSaveFoodEntry = view.findViewById(R.id.FTbtnSaveMess);
        btnSaveFoodEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                Intent saveEntryIntent = createFoodTrackerEntryIntent();
                foodTrackerItemDetails.setResult(FOODTRACKER_RESULT_SAVE, saveEntryIntent);
                foodTrackerItemDetails.finish();
                
            }
        });
        Log.i(ACTIVITY_NAME, "-- In onCreate, AFTER click SAVE");
        //// DELETE BUTTON
        Button btnDeleteFoodEntry = view.findViewById(R.id.FTbtnDeleteMesg);
        btnDeleteFoodEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent deleteEntryIntent = createFoodTrackerEntryIntent();
                foodTrackerItemDetails.setResult(FOODTRACKER_RESULT_DELETE, deleteEntryIntent);
                foodTrackerItemDetails.finish();
                
            }
        });
        
        return view;
    }
    
    public void setParent(FoodTrackerItemDetails parent) {
        Log.i(ACTIVITY_NAME, "-- In setParent");
        foodTrackerItemDetails = parent;
    }
    
    private Intent createFoodTrackerEntryIntent() {
        
        Intent resultIntent = new Intent();
        
        resultIntent.putExtra("ID", clickedItemID);
        
        ///// ADD limits here
        resultIntent.putExtra("Name", nameItem.getText().toString());
        
        resultIntent.putExtra("Cal", calValue.getText().toString());
        resultIntent.putExtra("Fat", fatValue.getText().toString());
        resultIntent.putExtra("Carbs", carbsValue.getText().toString());
        Log.i(ACTIVITY_NAME, "-- In createFoodTrackerEntryIntent");
        
        
        return resultIntent;
    }
}