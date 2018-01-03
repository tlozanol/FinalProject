package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Automobile_LoadingBar extends Activity {

    private static final String ACTIVITY_NAME = "Automobile_LoadingBar";

    TextView automobileLoadingBarText;
    ProgressBar automomobileLoadingBar;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile__loading_bar);

        automobileLoadingBarText = findViewById(R.id.automobileLoadBarText);
        automomobileLoadingBar = findViewById(R.id.automobileLoadingBar);

        LoadingBar loadBar = new LoadingBar();
        loadBar.execute();

    }



    private class LoadingBar extends AsyncTask<String, Integer, String >{

        @Override
        protected String doInBackground(String... arg) {
            Log.i(ACTIVITY_NAME, "-- In doInBackground()");

            while(progress <= 100){
                try{
                    Thread.sleep(10);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                publishProgress(progress++);
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... value){
            Log.i(ACTIVITY_NAME, "-- In onProgressUpdate()");

            automomobileLoadingBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result){
            Log.i(ACTIVITY_NAME, "-- In onPostExecute()");

            automobileLoadingBarText.setVisibility(View.INVISIBLE);
            automomobileLoadingBar.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(Automobile_LoadingBar.this, Automobile_History.class);
            startActivity(intent);

        }
    }
}
