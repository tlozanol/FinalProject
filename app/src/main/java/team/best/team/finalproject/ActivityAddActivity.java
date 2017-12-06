package team.best.team.finalproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityAddActivity extends Activity
{
    private static final String ACTIVITY_NAME = "ActivityAddActivity: ";

    private Spinner spinner;
    private MyEditTextDatePicker datePicker;
    private EditText editTime;
    private EditText editNotes;
    private Button buttonAdd;
    private Button buttonCancel;

    ArrayList<String> activityToInput = new ArrayList<>();

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate()");
        setContentView(R.layout.activity_add);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activitiesArray, android.R.layout.simple_spinner_item);
        spinner = findViewById(R.id.spinnerAddActivity);
        spinner.setAdapter(adapter);

        datePicker = new MyEditTextDatePicker(this, R.id.editTextActivityDate);
        editTime = findViewById(R.id.editTextActivityTime);
        editNotes = findViewById(R.id.editTextActivityNote);
        buttonAdd = findViewById(R.id.buttonActivityAdd);
        buttonCancel = findViewById(R.id.buttonActivityCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!datePicker.isSet())
                {
                    Toast.makeText(ActivityAddActivity.this, "You must enter a date", Toast.LENGTH_SHORT).show();
                }
                else //TODO: Add checks for remaining fields and throw popup to verify empty entry
                {
                    activityToInput.clear();
                    activityToInput.add(datePicker.getDate());
                    activityToInput.add(editTime.getText().toString());
                    activityToInput.add(spinner.getSelectedItem().toString());
                    activityToInput.add(editNotes.getText().toString());

                    dbHelper.addActivityDBEntry(activityToInput);

                    Toast.makeText(ActivityAddActivity.this, "Activity added", Toast.LENGTH_LONG).show();

                    spinner.setSelection(0);
                    datePicker.resetDisplay();
                    editTime.setText(R.string.activityTimeHint);
                    editNotes.setText(R.string.activityNotesHint);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
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
