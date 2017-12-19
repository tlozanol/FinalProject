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
    
    private Button FTbtnQuit;
    
    protected static final String ACTIVITY_NAME = "FoodTrackerItemFragment";
   
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
        //View view = inflater.inflate(R.layout.activity_food_tracker_item_fragment,  null);
       // TextView FTFragLabel = view.findViewById(R.id.FTFragLabel);
       // FTFragLabel.setText("APPLE");
      
        //////  ADD SNACKBAR TO SAVE BUTTON
          Button FTbtnSaveMess = view.findViewById(R.id.FTbtnSaveMess);
          FTbtnSaveMess.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Snackbar.make(view , "Your Food Item has been saved!", Snackbar.LENGTH_SHORT).show();
              }
          });
        
    
    
        
     
        return view;
    }

    
}