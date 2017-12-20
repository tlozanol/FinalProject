package team.best.team.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FoodTrackerLVwithMenu extends AppCompatActivity {
    
    protected static final String ACTIVITY_NAME = "FoodTrackerListVIewMenu";
    
    private final int FOODTRACKER_REQUEST_ADD = 10;
    private final int FOODTRACKER_REQUEST_EDIT = 20;
    private final int FOODTRACKER_RESULT_SAVE = 30; // cancel not included, since it acts as Back button
    private final int FOODTRACKER_RESULT_DELETE = 40;
    
    
    //// SETTING FOR THE DATABASE HERE
    ArrayList<ArrayList<String>> foodtrackerArray = new ArrayList<>();
    
    DatabaseHelper databaseHelper;
    ListView listFoodtracker;
    FoodTrackerLVwithMenu.FoodTrackerListAdapter foodTrackerListAdapter;
    SQLiteDatabase writableDatabase;
  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_lvwith_menu);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
    
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        foodtrackerArray = databaseHelper.getFoodTrackerDBData();
        
        listFoodtracker = findViewById(R.id.FTListView);
        foodTrackerListAdapter = new FoodTrackerListAdapter(this);
        listFoodtracker.setAdapter(foodTrackerListAdapter);
        
 
    
        listFoodtracker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long position) {
                Bundle thermostatEntryBundle = createFoodTrackerAddBundle();
                Intent goToThermostatEdit = new Intent(FoodTrackerLVwithMenu.this, ThermostatAddOrEditActivity.class);
                goToThermostatEdit.putExtras(thermostatEntryBundle);
                startActivityForResult(goToThermostatEdit, FOODTRACKER_REQUEST_EDIT);
            }
        });
        
            Button btnAddFoodItem = findViewById(R.id.FTbtnAddFoodItem);
        btnAddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle foodtrackerEntryBundle = createFoodTrackerAddBundle();
                Intent goToFoodTrackerAdd = new Intent(FoodTrackerLVwithMenu.this, FoodTrackerItemFragment.class);
                goToFoodTrackerAdd.putExtras(foodtrackerEntryBundle);
                startActivityForResult(goToFoodTrackerAdd, FOODTRACKER_REQUEST_ADD);
            }
        });
 
   
        
        
        
        ////    TOOLBAR CODING
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    
        //// ADD BUTTON
        Button btnAddFoodItem2 = findViewById(R.id.FTbtnAddFoodItem);
        btnAddFoodItem2.setOnClickListener(new View.OnClickListener(){
        
        
            @Override
            public void onClick(View view) {
                Intent goToFoodTrackerFragment = new Intent(FoodTrackerLVwithMenu.this, FoodTrackerItemDetails.class);
                startActivity(goToFoodTrackerFragment);
            }
        });
    
        //// QUIT BUTTON
        Button ftBtnQuit = findViewById(R.id.FTbtnQuit);
        ftBtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_ftBtnQuit = new AlertDialog.Builder(FoodTrackerLVwithMenu.this);
                alert_ftBtnQuit.setMessage("Do you want to Quit this app?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = alert_ftBtnQuit.create();
                alert.setTitle("ALERT!");
                alert.show();
            }
        });
     
        
    }
    
    private Bundle createFoodTrackerAddBundle() {
        Bundle foodtrackerEntryBundle = new Bundle();
        foodtrackerEntryBundle.putBoolean("InAdd", true);
        foodtrackerEntryBundle.putInt("ID", -1);
        foodtrackerEntryBundle.putString("Name", " ");
        foodtrackerEntryBundle.putString("Cal", "");
        foodtrackerEntryBundle.putString("Fat", "");
        
        return foodtrackerEntryBundle;
    }
    
    private Bundle createFoodTrackerEditBundle(int position) {
        int clickedItemID = databaseHelper.getFoodTrackerItemID(position);
        String clickedItemName = foodtrackerArray.get(position).get(1);
        String clickedCal = foodtrackerArray.get(position).get(2);
        String clickedFat = foodtrackerArray.get(position).get(3);
        
        
        Bundle foodtrackerEntryBundle = new Bundle();
        foodtrackerEntryBundle.putBoolean("InAdd", false);
        foodtrackerEntryBundle.putInt("ID", clickedItemID);
        foodtrackerEntryBundle.putString("Name", clickedItemName);
        foodtrackerEntryBundle.putString("Cal", clickedCal);
        foodtrackerEntryBundle.putString("Fat", clickedFat);
        
        return foodtrackerEntryBundle;
    }
    
    
    
    
    //// MENU OPTIONS AND PROPERTIES
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.foodtracker_my_navigation_menu, menu);
            return true;
        }
    
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle presses on the action bar items
            switch (item.getItemId()) {
                case R.id.action_one:
                    //show a Toast
                    Toast t = Toast.makeText(this, "Version 1.0, by Nathalie Beaudry", Toast.LENGTH_LONG);
                    t.show();
                    break;
                case R.id.action_two:
                    //launch another Activity
                    Toast t2 = Toast.makeText(this, "instructions", Toast.LENGTH_LONG);
                    t2.show();
                    break;
              
            }
            return true;
        }
    private class FoodTrackerListAdapter extends ArrayAdapter<String> {
        
        public FoodTrackerListAdapter(Context context) {
            super(context, 0);
            Log.i(ACTIVITY_NAME, "-- FoodTrackerListAdapter constructor");
        }
        
        @Override
        public int getCount() {
            return foodtrackerArray.size();
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.i(ACTIVITY_NAME, "-- ThermostatListAdapter getView()");
            LayoutInflater inflater = FoodTrackerLVwithMenu.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.foodtracker_itemlisted, null);
            
            TextView textFoodItemName = result.findViewById(R.id.FTtextVLblItemName);
          //  TextView textThermostatEntryTime = result.findViewById(R.id.textThermostatEntryTime);
           // TextView textThermostatEntryTemperature = result.findViewById(R.id.textThermostatEntryTemperature);
            
            // getItem(s) unnecessary since we are parsing through the ArrayList in getView
            ArrayList<String> row = foodtrackerArray.get(position);
    
            textFoodItemName.setText(row.get(1));
            //textThermostatEntryTime.setText(row.get(2));
            //textThermostatEntryTemperature.setText(row.get(3));
            
            return result;
        }
    }
    
    
    
    
    
}
