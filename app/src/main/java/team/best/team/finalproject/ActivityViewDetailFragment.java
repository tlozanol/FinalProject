package team.best.team.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityViewDetailFragment extends Fragment
{
    private static final String ACTIVITY_NAME = "ActivityViewDetail: ";

    private OnItemSelectedListener mListener;

    TextView textActivityDate;
    TextView textActivityActivity;
    TextView textActivityTime;
    TextView textActivityNotes;

    Button buttonActivityEdit;
    Button buttonActivityDelete;

    ArrayList<String> currentActivity;

    public ActivityViewDetailFragment() { Log.i(ACTIVITY_NAME, "in constructor"); }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in onCreateView");
        View view = inflater.inflate(R.layout.fragment_activity_view_detail, container, false);

        currentActivity = getArguments().getStringArrayList(ActivityViewHistory.ACTIVITY);

        textActivityDate = view.findViewById(R.id.textActivityDate);
            textActivityDate.setText(currentActivity.get(1));
        textActivityActivity = view.findViewById(R.id.textActivityActivity);
            textActivityActivity.setText(currentActivity.get(3));
        textActivityTime = view.findViewById(R.id.textActivityTime);
            textActivityTime.setText(currentActivity.get(2) + " minutes");
        textActivityNotes = view.findViewById(R.id.textActivityNotes);
            textActivityNotes.setText(currentActivity.get(4));

        return view;
    }

    public void onButtonPressed(Uri uri)
    {
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener)
        {
            mListener = (OnItemSelectedListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemSelectedListener
    {
        void onSelection(ArrayList<String> activity);
    }
}
