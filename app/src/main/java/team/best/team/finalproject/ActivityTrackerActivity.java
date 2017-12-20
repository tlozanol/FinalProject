package team.best.team.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityTrackerActivity extends Activity {

    private static final String ACTIVITY_NAME = "ActivityTracker: ";

    private TextView currentLabel;
    private TextView prevLabel;
    private TextView currentSummary;
    private TextView prevSummary;
    private ProgressBar progressBar;

    ArrayList<ArrayList<String>> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate()");
        setContentView(R.layout.activity_tracker);

        currentSummary = findViewById(R.id.currentSummary);
        prevSummary = findViewById(R.id.prevSummary);
        currentLabel = findViewById(R.id.currentSummaryLabel);
        prevLabel = findViewById(R.id.prevSummaryLabel);
        progressBar = findViewById(R.id.progressbar);
    }

    public void onViewHistoryClick(View view)
    {
        Intent intent = new Intent(this, ActivityViewHistory.class);
        startActivity(intent);
    }

    public void onAddActivityClick(View view)
    {
        Intent intent = new Intent(this, ActivityAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(ACTIVITY_NAME, "in onResume()");

        currentLabel.setVisibility(TextView.INVISIBLE);
        prevLabel.setVisibility(TextView.INVISIBLE);
        currentSummary.setVisibility(TextView.INVISIBLE);
        prevSummary.setVisibility(TextView.INVISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        ActivityList al = new ActivityList();
        al.execute(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(ACTIVITY_NAME, "in onStart()");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(ACTIVITY_NAME, "in onPause()");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(ACTIVITY_NAME, "in onStop()");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "in onDestroy()");
    }

    private class ActivityList extends AsyncTask<Context, Integer, ArrayList>
    {
        int currentTotal = 0;
        int prevTotal = 0;

        ActivityList(){}

        @Override
        protected ArrayList doInBackground(Context... contexts)
        {
            Log.i(ACTIVITY_NAME, "in doInBackground");
            DatabaseHelper dbHelper;
            ArrayList<ArrayList<String>> listFromDB;
            dbHelper = new DatabaseHelper(contexts[0]);
            listFromDB = dbHelper.getActivityDBData();

            for(ArrayList<String> activity : listFromDB)
            {
                String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                int currentInt = Integer.parseInt(currentDate.substring(0,6));
                String activityDate = activity.get(2);
                int activityInt = Integer.parseInt(activityDate.substring(0,6));

                Log.i(ACTIVITY_NAME, "current date: " + currentDate);
                Log.i(ACTIVITY_NAME, "activity date: " + activityDate);

                if(currentInt == activityInt)
                {
                    int tempCurrent = activity.get(3).isEmpty() ? 0 : Integer.parseInt(activity.get(3));
                    currentTotal += tempCurrent;
                }
                if(currentInt - 1 == activityInt)
                {
                    int tempPrev = activity.get(3).isEmpty() ? 0 : Integer.parseInt(activity.get(3));
                    prevTotal += tempPrev;
                }

                float progress = (1/Float.valueOf(listFromDB.size()) * 100);

                publishProgress((int)progress);
            }

            //For demo purposes only to show progress bar
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return listFromDB;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            progressBar.incrementProgressBy(progress[0]);
        }

        @Override
        protected void onPostExecute(ArrayList list)
        {
            Log.i(ACTIVITY_NAME, "in onPostExecute");

            String cSum = String.valueOf(currentTotal);
            String pSum = String.valueOf(prevTotal);

            progressBar.setVisibility(ProgressBar.INVISIBLE);
            currentLabel.setVisibility(TextView.VISIBLE);
            prevLabel.setVisibility(TextView.VISIBLE);
            currentSummary.setVisibility(TextView.VISIBLE);
            prevSummary.setVisibility(TextView.VISIBLE);

            String cText = currentTotal > 0 ? getString(R.string.minutes) + getString(R.string.currentTimeGood) : getString(R.string.minutes) + getString(R.string.currentTimeBad);
            String pText = prevTotal > 0 ? getString(R.string.minutes) + getString(R.string.prevTimeGood) : getString(R.string.minutes) + getString(R.string.prevTimeBad);

            currentSummary.setText(cSum + cText);
            prevSummary.setText(pSum + pText);

            activities = list;
        }
    }
}
