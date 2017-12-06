package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ActivityViewHistory extends Activity implements ActivityViewDetailFragment.OnItemSelectedListener
{

    private static final String ACTIVITY_NAME = "ActivityHistory: ";
    public static final String ACTIVITY = "Activity to pass";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate()");
        setContentView(R.layout.activity_view_history);

        Bundle bundle = getIntent().getExtras();

        ActivityViewHistoryFragment historyFragment = new ActivityViewHistoryFragment();
        historyFragment.setArguments(bundle);
        FragmentTransaction activityFT = getFragmentManager().beginTransaction();
        activityFT.replace(R.id.activityFrame, historyFragment);
        activityFT.commit();
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

    @Override
    public void onSelection(ArrayList<String> activity)
    {
        Bundle actBundle = new Bundle();

        actBundle.putStringArrayList(ACTIVITY, activity);
        ActivityViewDetailFragment detailFragment = new ActivityViewDetailFragment();
        detailFragment.setArguments(actBundle);
        FragmentTransaction activityFT = getFragmentManager().beginTransaction();
        activityFT.replace(R.id.activityFrame, detailFragment);
        activityFT.commit();
    }
}
