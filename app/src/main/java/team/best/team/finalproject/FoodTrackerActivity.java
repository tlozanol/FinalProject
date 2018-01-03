package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

//// SPLASHSCREEN
public class FoodTrackerActivity extends AppCompatActivity {
    private ProgressBar myProgressBar;
    //private Button btnProgressBar;
    
    protected static final String ACTIVITY_NAME = "FoodTrackerActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        Log.i(ACTIVITY_NAME, "In onCreate");
        
        myProgressBar = (ProgressBar) findViewById(R.id.FT_splashScreen_progressBar);
        
        Button btnStartListView = findViewById(R.id.button3);
        btnStartListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start FoodTracker Button");
                Downloader downloader = new Downloader();
                downloader.execute();
            }
        });
        
        ////    TOOLBAR MENU CODING
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        
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
//                case R.id.action_two:
//                    //launch another Activity
//                    Toast t2 = Toast.makeText(this, "instructions", Toast.LENGTH_LONG);
//                    t2.show();
//                    break;
        
        }
        return true;
    }
    
    
    //ASYNC TASK FOR PROGRESS BAR
    private class Downloader extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //SETS THE PROGRESS BAR PROPERTIES
            myProgressBar.setMax(100);
        }
        
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            myProgressBar.setVisibility(View.VISIBLE);
            //UPDATES THE PROGRESS BAR HERE
            myProgressBar.setProgress(values[0]);
        }
        
        @Override
        protected Integer doInBackground(Void... arg0) {
            //DOES HEAVY JOB HERE
            
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(4);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            //CREATE TOAST TO ANNOUNCE THAT DOWNLOAD IS DONE
            Toast.makeText(getApplicationContext(), "Download Finished!", Toast.LENGTH_SHORT).show();
            
            Intent goToFoodTrackerList = new Intent(FoodTrackerActivity.this, FoodTrackerLVwithMenu.class);
            startActivity(goToFoodTrackerList);
            
            myProgressBar.setVisibility(View.INVISIBLE);
        }
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }
    
}
