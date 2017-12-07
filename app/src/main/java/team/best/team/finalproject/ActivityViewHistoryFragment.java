package team.best.team.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ActivityViewHistoryFragment extends Fragment
{
    private static final String ACTIVITY_NAME = "View history fragment: ";

    private ActivityViewDetailFragment.OnItemSelectedListener mListener;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    private ArrayList<ArrayList<String>> listFromDB;
    private ArrayList<String> selection;

    public ActivityViewHistoryFragment() { Log.i(ACTIVITY_NAME, "in constructor"); }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate");

        dbHelper = new DatabaseHelper(getActivity());
        database = dbHelper.getWritableDatabase();
        setMListener(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in onCreateView()");
        View view = inflater.inflate(R.layout.fragment_activity_view_history, container, false);

        listFromDB = dbHelper.getActivityDBData();

        ListView historyList = view.findViewById(R.id.listActivity);
        historyList.setAdapter(new ActivityListAdapter(getActivity()));
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id)
            {
                if (mListener != null)
                {
                    selection = listFromDB.get(pos);
                    mListener.onSelection(selection);
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.i(ACTIVITY_NAME, "in onAttach");
        super.onAttach(context);
    }

    public void setMListener(Context context)
    {
        if (context instanceof ActivityViewDetailFragment.OnItemSelectedListener)
        {
            mListener = (ActivityViewDetailFragment.OnItemSelectedListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach()
    {
        Log.i(ACTIVITY_NAME, "in onDetach");
        super.onDetach();
        mListener = null;
    }
    private class ActivityListAdapter extends ArrayAdapter<String>
    {

        ActivityListAdapter(Context context)
        {
            super(context, 0);
            Log.i(ACTIVITY_NAME, "in ActivityListAdapter constructor");
        }

        @Override
        public int getCount() { return listFromDB.size(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.activity_entry, null);

            TextView textListDate = result.findViewById(R.id.activityListDate);
            TextView textListActivity = result.findViewById(R.id.activityListActivity);
            TextView textListTime = result.findViewById(R.id.activityListTime);

            ArrayList<String> row = listFromDB.get(position);

            textListDate.setText(row.get(1) + " - ");
            textListActivity.setText(row.get(3) + " - ");
            textListTime.setText(row.get(2) + " minutes");

            return result;
        }
    }
}
