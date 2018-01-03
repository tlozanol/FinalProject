package team.best.team.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

public class ActivityAddActivity extends AppCompatActivity
{
    private static final String ACTIVITY_NAME = "ActivityAddActivity: ";

    private boolean editing = false;
    private int id;

    private TextView title;
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

        title = findViewById(R.id.textActivityAddTitle);
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
                    Toast.makeText(ActivityAddActivity.this, R.string.dateToast, Toast.LENGTH_SHORT).show();
                }
                else if(isEmpty(editTime) || isEmpty(editNotes))
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ActivityAddActivity.this);
                    StringBuilder missing = new StringBuilder(getString(R.string.inputMissing));
                    missing = isEmpty(editTime) ? missing.append(getString(R.string.activityTime)) : missing.append("");
                    if(isEmpty(editTime) && isEmpty(editNotes)) missing.append(getString(R.string.and));
                    missing = isEmpty(editNotes) ? missing.append(getString(R.string.activityNotesTitle)) : missing.append("");
                    alertBuilder.setMessage(missing.append(getString(R.string.saveAnyway)).toString());

                    alertBuilder.setCancelable(true);

                    alertBuilder.setNegativeButton(
                            getString(R.string.yes),
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    saveActivity();
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
                else
                { saveActivity(); }
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

    boolean isEmpty(EditText editText) { return editText.getText().toString().trim().length() == 0; }

    void saveActivity()
    {
        activityToInput.clear();
        activityToInput.add(datePicker.getDate());
        activityToInput.add(String.valueOf(datePicker.getIntDate()));
        activityToInput.add(editTime.getText().toString());
        activityToInput.add(spinner.getSelectedItem().toString());
        activityToInput.add(editNotes.getText().toString());

        if(editing)
        {
            dbHelper.updateActivityDBEntry(id, activityToInput);
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.editedSnack, Snackbar.LENGTH_LONG).show();

            activityToInput.add(0,String.valueOf(id));
            setResult(ActivityViewHistory.RES_EDIT_CODE, new Intent().putStringArrayListExtra(ActivityViewHistory.ACTIVITY, activityToInput));
        }
        else
        {
            dbHelper.addActivityDBEntry(activityToInput);
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.addedSnack, Snackbar.LENGTH_LONG).show();

            activityToInput.add(0,String.valueOf(dbHelper.getActivityItemID(-1)));
            setResult(ActivityViewHistory.RES_ADD_CODE, new Intent().putStringArrayListExtra(ActivityViewHistory.ACTIVITY, activityToInput));

            spinner.setSelection(0);
            datePicker.resetDisplay();
            editTime.setText(null);
            editTime.setHint(R.string.activityTimeHint);
            editNotes.setText(null);
            editNotes.setHint(R.string.activityNotesHint);
        }

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

        editing = getIntent().getBooleanExtra(ActivityViewHistory.EDIT_BOOL, false);

        if(editing)
        {
            activityToInput.clear();
            activityToInput = getIntent().getStringArrayListExtra(ActivityViewHistory.ACTIVITY);
            id = Integer.parseInt(activityToInput.get(0));
            title.setText(R.string.activityEditTitle);

            if(activityToInput.get(4).equals(getString(R.string.running))) spinner.setSelection(0);
            else if(activityToInput.get(4).equals(getString(R.string.walking))) spinner.setSelection(1);
            else if(activityToInput.get(4).equals(getString(R.string.biking))) spinner.setSelection(2);
            else if(activityToInput.get(4).equals(getString(R.string.swimming))) spinner.setSelection(3);
            else if(activityToInput.get(4).equals(getString(R.string.skating))) spinner.setSelection(4);

            datePicker.setDisplay(activityToInput.get(1), activityToInput.get(2));
            editTime.setText(activityToInput.get(3));
            editNotes.setText(activityToInput.get(5));

            buttonAdd.setText(R.string.buttonSave);
        }
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
