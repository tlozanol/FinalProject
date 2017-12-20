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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ActivityViewHistoryFragment extends Fragment
{
    private static final String ACTIVITY_NAME = "View history fragment: ";

    private ActivityViewDetailFragment.OnItemSelectedListener mListener;

    static DatabaseHelper dbHelper;
    SQLiteDatabase database;
    static ActivityListAdapter adapter;

    private static ArrayList<ArrayList<String>> listFromDB;
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
        adapter = new ActivityListAdapter(getActivity());

        historyList.setAdapter(adapter);
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

        Button buttonSortDay = view.findViewById(R.id.buttonSortDay);
        Button buttonSortTotal = view.findViewById(R.id.buttonSortTotal);
        Button buttonAddNew = view.findViewById(R.id.buttonAddNew);

        buttonSortDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Collections.sort(listFromDB, new Comparator<ArrayList<String>>()
                {
                    @Override
                    public int compare(ArrayList<String> o1, ArrayList<String> o2)
                    {

                        Integer arg1 = o1.get(2).trim().length() == 0 ? 0 : Integer.parseInt(o1.get(2));
                            System.out.println(o1.get(2));

                        Integer arg2 = o2.get(2).trim().length() == 0 ? 0 : Integer.parseInt(o2.get(2));
                            System.out.println(o2.get(2));

                        return arg1.compareTo(arg2);
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        buttonSortTotal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Collections.sort(listFromDB, new Comparator<ArrayList<String>>()
                {
                    @Override
                    public int compare(ArrayList<String> o1, ArrayList<String> o2)
                    {
                        Integer arg1 = o1.get(3).trim().length() == 0 ? 0 : Integer.parseInt(o1.get(3));
                        Integer arg2 = o2.get(3).trim().length() == 0 ? 0 : Integer.parseInt(o2.get(3));

                        return arg2.compareTo(arg1);
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        buttonAddNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onAdd();
            }
        });

        return view;
    }

    public static void updateList()
    {
        listFromDB.clear();
        listFromDB = dbHelper.getActivityDBData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context)
    {
        Log.i(ACTIVITY_NAME, "in onAttach");
        super.onAttach(context);
        setMListener(getActivity());
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

            String date = row.get(1) + " - ";
            String activity = row.get(4) + " - ";
            String time = row.get(3).isEmpty() ? "0 " + getString(R.string.minutes) : row.get(3) + getString(R.string.minutes);

            textListDate.setText(date);
            textListActivity.setText(activity);
            textListTime.setText(time);

            return result;
        }
    }
}
