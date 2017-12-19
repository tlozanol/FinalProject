package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FoodTrackerActivity extends Activity {
    private ProgressBar myProgressBar;
    private Button btnProgressBar;
   
    protected static final String ACTIVITY_NAME = "FoodTrackerActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        Log.i(ACTIVITY_NAME, "In onCreate");
       
       Button btnStartListView = findViewById(R.id.button3);
       btnStartListView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                new Downloader().execute();
           }
        });
        myProgressBar = (ProgressBar) findViewById(R.id.FT_splashScreen_progressBar);
    }
    //ASYNC TASK FOR PROGRESS BAR
     class Downloader extends AsyncTask<Void, Integer, Integer>{
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
            
            for(int i=0;i<100;i++){
                publishProgress(i);
                try{
                    Thread.sleep(100);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
           return null;
         }
         
         @Override
         protected void onPostExecute(Integer result){
             super.onPostExecute(result);
            //CREATE TOAST TO ANNOUNCE THAT DOWNLOAD IS DONE
             Toast.makeText(getApplicationContext(), "Download Finished!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FoodTrackerActivity.this, FoodTrackerLVwithMenu.class);
             startActivity(intent);
         }
        
     }
    
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }
    
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");
    }
    
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }
    
}
