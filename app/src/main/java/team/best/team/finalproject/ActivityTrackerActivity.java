package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityTrackerActivity extends Activity {

    private static final String ACTIVITY_NAME = "ActivityTracker: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate()");
        setContentView(R.layout.activity_tracker);
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
}
