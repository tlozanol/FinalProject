package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ActivityViewHistory extends Activity implements ActivityViewDetailFragment.OnItemSelectedListener
{

    private static final String ACTIVITY_NAME = "ActivityHistory: ";
    public static final String ACTIVITY = "Activity to pass";
    public static final String EDIT_BOOL = "Edit";
    public static final int REQ_CODE = 100;
    public static final int RES_EDIT_CODE = 200;
    public static final int RES_ADD_CODE = 300;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    ArrayList<String> activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate()");
        setContentView(R.layout.activity_view_history);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();

        ActivityViewHistoryFragment historyFragment = new ActivityViewHistoryFragment();
        historyFragment.setArguments(bundle);
        FragmentTransaction activityFT = getFragmentManager().beginTransaction();
        activityFT.replace(R.id.activityFrame, historyFragment);
        activityFT.commit();
    }

    @Override
    public void onBackPressed()
    {
        ActivityViewHistoryFragment.updateList();
        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStackImmediate();
        }
        else { super.onBackPressed(); }
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

    public void onAddActivityClick(View view)
    {
        Intent intent = new Intent(this, ActivityAddActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    public void onSelection(ArrayList<String> activity)
    {
        this.activity = activity;

        Bundle actBundle = new Bundle();

        actBundle.putStringArrayList(ACTIVITY, activity);
        ActivityViewDetailFragment detailFragment = new ActivityViewDetailFragment();
        detailFragment.setArguments(actBundle);
        FragmentTransaction activityFT = getFragmentManager().beginTransaction();
        activityFT.replace(R.id.activityFrame, detailFragment);
        activityFT.addToBackStack(null);
        activityFT.commit();
    }

    @Override
    public void onDelete(final String id)
    {
        Log.i(ACTIVITY_NAME, "in onDelete");

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        String message = "Are you sure you want to delete?";
        alertBuilder.setMessage(message);

        alertBuilder.setCancelable(true);

        alertBuilder.setNegativeButton(
                getString(R.string.yes),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dbHelper.deleteActivityDBEntry(Integer.parseInt(id));
                        onBackPressed();
                    }
                });

        alertBuilder.setPositiveButton(
                getString(R.string.no),
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onEdit(ArrayList<String> activity)
    {
        Log.i(ACTIVITY_NAME, "in onEdit");
        Intent intent = new Intent(this, ActivityAddActivity.class);
        intent.putExtra(EDIT_BOOL, true);
        intent.putStringArrayListExtra(ACTIVITY, activity);

        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    public void onAdd()
    {
        startActivityForResult(new Intent(this, ActivityAddActivity.class), RES_ADD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RES_EDIT_CODE)
        {
            onBackPressed();
            onSelection(data.getStringArrayListExtra(ACTIVITY));
        } else if(resultCode == RES_ADD_CODE)
        {
            ActivityViewHistoryFragment.updateList();
        }
    }
}
