package team.best.team.finalproject;

import android.content.Context;
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

    private String id;
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
        setMListener(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in onCreateView");
        View view = inflater.inflate(R.layout.fragment_activity_view_detail, container, false);

        currentActivity = getArguments().getStringArrayList(ActivityViewHistory.ACTIVITY);

        id = currentActivity.get(0);

        for(int i = 0; i<6; i++)
        { System.out.println(currentActivity.get(i));}

        textActivityDate = view.findViewById(R.id.textActivityDate);
            textActivityDate.setText(currentActivity.get(1));
        textActivityActivity = view.findViewById(R.id.textActivityActivity);
            textActivityActivity.setText(currentActivity.get(4));
        textActivityTime = view.findViewById(R.id.textActivityTime);
            String time = currentActivity.get(3).isEmpty() ? "0 " + getString(R.string.minutes) : currentActivity.get(3) + getString(R.string.minutes);
            textActivityTime.setText(time);
        textActivityNotes = view.findViewById(R.id.textActivityNotes);
            textActivityNotes.setText(currentActivity.get(5));

        buttonActivityEdit = view.findViewById(R.id.buttonActivityEdit);
        buttonActivityDelete = view.findViewById(R.id.buttonActivityDelete);

        buttonActivityEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onEdit(currentActivity);
            }
        });

        buttonActivityDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onDelete(id);
            }
        });

        return view;
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
    public void onAttach(Context context)
    {
        super.onAttach(context);
        setMListener(getActivity());
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
        void onDelete(String id);
        void onEdit(ArrayList<String> activity);
        void onAdd();
    }
}
