package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThermostatAddOrEditActivity extends Activity {
    
    private static final String ACTIVITY_NAME = "ThermostatAddOrEditA";
    
    TextView thermostatProgressBarText;
    ProgressBar thermostatProgressBar;
    int progress = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit_thermostat_entry);
        Log.i(ACTIVITY_NAME, "-- In onCreate()");
    
        thermostatProgressBarText = findViewById(R.id.thermostatProgressBarText);
        thermostatProgressBar = findViewById(R.id.thermostatProgressBar);
    
        LoadingWindow loadingWindow = new LoadingWindow();
        loadingWindow.execute();
    }
    
    private void launchInputFragment() {
        Log.i(ACTIVITY_NAME, "-- In launchInputFragment()");
        
        Bundle thermostatEntryBundle = getIntent().getExtras();
        
        ThermostatInputFragment thermostatFrag = new ThermostatInputFragment();
        thermostatFrag.setParent(ThermostatAddOrEditActivity.this); // sets parent so this can be used by frag
        thermostatFrag.setArguments(thermostatEntryBundle);
        FragmentManager thermostatFragManager = getFragmentManager();
        FragmentTransaction thermostatFragTransaction = thermostatFragManager.beginTransaction();
        thermostatFragTransaction.replace(R.id.frameLayout, thermostatFrag);
        thermostatFragTransaction.commit();
    }
    
    
    private class LoadingWindow extends AsyncTask<String, Integer, String> {
        
        @Override
        protected String doInBackground(String... arg) {
            Log.i(ACTIVITY_NAME, "-- In doInBackground()");
            
            while (progress <= 100) {
                try {
                    Thread.sleep(8);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                publishProgress(progress++);
            }
            
            return "";
        }
        
        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(ACTIVITY_NAME, "-- In onProgressUpdate()");
            
            thermostatProgressBar.setProgress(value[0]);
        }
        
        @Override
        protected void onPostExecute(String result) {
            Log.i(ACTIVITY_NAME, "-- In onPostExecute()");
            
            thermostatProgressBarText.setVisibility(View.INVISIBLE);
            thermostatProgressBar.setVisibility(View.INVISIBLE);
            
            launchInputFragment();
        }
    }
    
}




