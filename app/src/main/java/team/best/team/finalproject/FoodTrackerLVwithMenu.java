package team.best.team.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class FoodTrackerLVwithMenu extends Activity {
    
    protected static final String ACTIVITY_NAME = "FoodTrackerListVIewMenu";
    
    private final int FOODTRACKER_REQUEST_ADD = 10;
    private final int FOODTRACKER_REQUEST_EDIT = 20;
    private final int FOODTRACKER_RESULT_SAVE = 100; // cancel not included, since it acts as Back button
    private final int FOODTRACKER_RESULT_DELETE = 200;
    
    //// SETTING FOR THE DATABASE HERE
    ArrayList<ArrayList<String>> foodtrackerArray = new ArrayList<>();
    
    DatabaseHelper databaseHelper;
    ListView ftListView;
    FoodTrackerListAdapter foodtrackerListAdapter;
    SQLiteDatabase writableDatabase;
    
    Button buttonAddFoodTracker;
    Button buttonHelpFoodTracker;
    
    RelativeLayout foodActivityRelativeLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_lvwith_menu);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
        
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        foodtrackerArray = databaseHelper.getFoodTrackerDBData();
    
        foodActivityRelativeLayout = findViewById(R.id.foodActivityRelativeLayout);
        
        
        //// LISTVIEW
        ftListView = findViewById(R.id.ftListView);
        foodtrackerListAdapter = new FoodTrackerListAdapter(this);
        ftListView.setAdapter(foodtrackerListAdapter);
        ftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long position) {
                Bundle foodtrackerEntryBundle = createFoodTrackerEditBundle((int) position);
                Intent goToFoodTrackerEdit = new Intent(FoodTrackerLVwithMenu.this, FoodTrackerItemDetails.class);
                goToFoodTrackerEdit.putExtras(foodtrackerEntryBundle);
                startActivityForResult(goToFoodTrackerEdit, FOODTRACKER_REQUEST_EDIT);
            }
        });
        
        //// ADD BUTTON
        Button btnAddFoodItem = findViewById(R.id.FTbtnAddFoodItem);
        btnAddFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle foodtrackerEntryBundle = createFoodTrackerAddBundle();
                Intent goToFoodTrackerFragment = new Intent(FoodTrackerLVwithMenu.this, FoodTrackerItemDetails.class);
                goToFoodTrackerFragment.putExtras(foodtrackerEntryBundle);
                startActivityForResult(goToFoodTrackerFragment, FOODTRACKER_REQUEST_ADD);
            }
        });
        
        //// HELP BUTTON
        Button btnFoodTrackerHelp = findViewById(R.id.FTbtnHelp);
        btnFoodTrackerHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFoodTrackerHelp = new Intent(FoodTrackerLVwithMenu.this, FoodTrackerHelp.class);
                startActivity(goToFoodTrackerHelp);
            }
        });
        /*
        ////    TOOLBAR MENU CODING
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/
        
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
        foodtrackerEntryBundle.putString("Carbs", "");
        
        return foodtrackerEntryBundle;
    }
    
    private Bundle createFoodTrackerEditBundle(int position) {
        int clickedItemID = databaseHelper.getFoodTrackerItemID(position);
        String clickedItemName = foodtrackerArray.get(position).get(1);
        String clickedCal = foodtrackerArray.get(position).get(2);
        String clickedFat = foodtrackerArray.get(position).get(3);
        String clickedCarbs = foodtrackerArray.get(position).get(4);
        
        
        Bundle foodtrackerEntryBundle = new Bundle();
        foodtrackerEntryBundle.putBoolean("InAdd", false);
        foodtrackerEntryBundle.putInt("ID", clickedItemID);
        foodtrackerEntryBundle.putString("Name", clickedItemName);
        foodtrackerEntryBundle.putString("Cal", clickedCal);
        foodtrackerEntryBundle.putString("Fat", clickedFat);
        foodtrackerEntryBundle.putString("Carbs", clickedCarbs);
        
        return foodtrackerEntryBundle;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "-- In onResume()");
        
        refreshFoodTrackerListView();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(ACTIVITY_NAME, "-- In onActivityResult()");
        
        if (requestCode == FOODTRACKER_REQUEST_ADD) {
            if (resultCode == FOODTRACKER_RESULT_SAVE) {
                // Save in Add
                Log.i(ACTIVITY_NAME, "---- Clicked Save in Add");
    
                ////  ADD SNACKBAR TO SAVE BUTTON
                Snackbar.make(foodActivityRelativeLayout, "Your Food Item has been saved!", Snackbar.LENGTH_SHORT).show();
                String newName = data.getStringExtra("Name");
                String newCal = data.getStringExtra("Cal");
                String newFat = data.getStringExtra("Fat");
                String newCarbs = data.getStringExtra("Carbs");
                
                Log.i(ACTIVITY_NAME, "-- After getExtras");
                
                ArrayList<String> dataToDB = new ArrayList<>();
                dataToDB.add(newName);
                dataToDB.add(newCal);
                dataToDB.add(newFat);
                dataToDB.add(newCarbs);
    
                Log.i(ACTIVITY_NAME, "-- After adding to ArrayList");
    
                Log.i(ACTIVITY_NAME, "-- About to insert data into DB:");
                Log.i(ACTIVITY_NAME, "-- Name: " + newName);
                Log.i(ACTIVITY_NAME, "-- Cal: " + newCal);
                Log.i(ACTIVITY_NAME, "-- Fat: " + newFat);
                Log.i(ACTIVITY_NAME, "-- Carbs: " + newCarbs);
    
                databaseHelper.addFoodTrackerDBEntry(dataToDB);
                
                Log.i(ACTIVITY_NAME, "-- After adding to database");
            }
            
        } else if (requestCode == FOODTRACKER_REQUEST_EDIT) {
            if (resultCode == FOODTRACKER_RESULT_SAVE) {
                // Save in Edit
                //Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Save");
                
                int IDToUpdate = data.getIntExtra("ID", 1);
                String newName = data.getStringExtra("Name");
                String newCal = data.getStringExtra("Cal");
                String newFat = data.getStringExtra("Fat");
                String newCarbs = data.getStringExtra("Carbs");
                
                ArrayList<String> dataToUpdateInDB = new ArrayList<>();
                dataToUpdateInDB.add(newName);
                dataToUpdateInDB.add(newCal);
                dataToUpdateInDB.add(newFat);
                dataToUpdateInDB.add(newCarbs);
                
                databaseHelper.updateFoodTrackerDBEntry(IDToUpdate, dataToUpdateInDB);
            } else if (resultCode == FOODTRACKER_RESULT_DELETE) {
                // Delete in Edit
                //Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Delete");
                
                int IDToDelete = data.getIntExtra("ID", 0);
                
                databaseHelper.deleteFoodTrackerDBEntry(IDToDelete);
            }
            
        }
    }
    
    
    private void refreshFoodTrackerListView() {
        
        databaseHelper = new DatabaseHelper(this);
        writableDatabase = databaseHelper.getWritableDatabase();
        foodtrackerArray = databaseHelper.getFoodTrackerDBData();
        foodtrackerListAdapter = new FoodTrackerListAdapter(this);
        ftListView.setAdapter(foodtrackerListAdapter);
    }
    /*
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
//                case R.id.action_two:
//                    //launch another Activity
//                    Toast t2 = Toast.makeText(this, "instructions", Toast.LENGTH_LONG);
//                    t2.show();
//                    break;
        
        }
        return true;
    }*/
    
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
            
            LayoutInflater inflater = FoodTrackerLVwithMenu.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.foodtracker_entry, null);
            
            TextView editTextItemName = result.findViewById(R.id.textFoodTrackerName);
            TextView editTextCal = result.findViewById(R.id.textFoodTrackerCal);
            TextView editTextFat = result.findViewById(R.id.textFoodTrackerFat);
            TextView editTextCarbs = result.findViewById(R.id.textFoodTrackerCarbs);
            
    
            
            // getItem(s) unnecessary since we are parsing through the ArrayList in getView
            ArrayList<String> row = foodtrackerArray.get(position);
            
            Log.i(ACTIVITY_NAME, "---- ListView entry");
            Log.i(ACTIVITY_NAME, "---- name: " + row.get(1));
            Log.i(ACTIVITY_NAME, "---- cal: " + row.get(2));
            Log.i(ACTIVITY_NAME, "---- carbs: " + row.get(4));
            
            editTextItemName.setText(row.get(1));
            editTextCal.setText("Cal: " +row.get(2));
            editTextFat.setText("Fat: " + row.get(3));
            editTextCarbs.setText("Carbs: " + row.get(4));
            
            return result;
        }
    }
    
    
}
