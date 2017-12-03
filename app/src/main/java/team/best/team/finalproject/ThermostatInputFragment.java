package team.best.team.finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ThermostatInputFragment extends Fragment {
    
    private static final String ACTIVITY_NAME = "ThermostatInputFragment";
    
    private final int THERMOSTAT_RESULT_SAVE = 100; // cancel not included, since it acts as Back button
    private final int THERMOSTAT_RESULT_DELETE = 200; // TODO make originals public static, access from ThermoAct
    
    private ThermostatAddOrEditActivity thermostatAddOrEditActivity; // parent class. used to setResult and finish
    
    private int clickedItemID;
    private NumberPicker pickerThermostatDay;
    private NumberPicker pickerThermostatHour;
    private NumberPicker pickerThermostatMinute;
    private NumberPicker pickerThermostatAMPM;
    private EditText editTextThermostatTemperature;
    
    private String[] daysOfTheWeek;
    
    public ThermostatInputFragment() {
        Log.i(ACTIVITY_NAME, "-- In constructor");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "-- In onCreate");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "-- In onCreateView");
        
        View view = inflater.inflate(R.layout.thermostat_input, null);
        
        Bundle thermostatEntryBundle = getArguments();
        boolean inAdd = thermostatEntryBundle.getBoolean("InAdd", false);
        clickedItemID = thermostatEntryBundle.getInt("ID", -1);
        String clickedDay = thermostatEntryBundle.getString("Day", "");
        String clickedTime = thermostatEntryBundle.getString("Time", "");
        String clickedTemperature = thermostatEntryBundle.getString("Temperature", " ");
    
    
        // need to set up weekdays here, after context has been acquired
        // reference on how to get R.strings: https://stackoverflow.com/questions/1983548/eclipse-java-values-in-r-string-return-int
        daysOfTheWeek = new String[]{
                getResources().getString(R.string.sunday),
                getResources().getString(R.string.monday),
                getResources().getString(R.string.tuesday),
                getResources().getString(R.string.wednesday),
                getResources().getString(R.string.thursday),
                getResources().getString(R.string.friday),
                getResources().getString(R.string.saturday)};
    
        pickerThermostatDay = view.findViewById(R.id.pickerThermostatDay);
        pickerThermostatDay.setMinValue(0);
        pickerThermostatDay.setMaxValue(6);
        pickerThermostatDay.setDisplayedValues(daysOfTheWeek);
        pickerThermostatDay.setValue(convertStringToNumberDay(clickedDay));
        pickerThermostatDay.setWrapSelectorWheel(false);
    
        int[] timeNumbers = new int[3];
        if (clickedTime.equals("")) {
            timeNumbers[0] = 1;
            timeNumbers[1] = 0;
            timeNumbers[2] = 0;
        }
        else
            timeNumbers = convertStringToNumbersTime(clickedTime);
    
        Log.i(ACTIVITY_NAME, "-- -- -- PASSED TO HERE 2");
        Log.i(ACTIVITY_NAME, "-- -- -- timeNumber[0]: " + timeNumbers[0]);
        Log.i(ACTIVITY_NAME, "-- -- -- timeNumber[1]: " + timeNumbers[1]);
        Log.i(ACTIVITY_NAME, "-- -- -- timeNumber[2]: " + timeNumbers[2]);
    
        pickerThermostatHour = view.findViewById(R.id.pickerThermostatHour);
        pickerThermostatHour.setMinValue(1);
        pickerThermostatHour.setMaxValue(12);
        pickerThermostatHour.setValue(timeNumbers[0]);
        pickerThermostatHour.setWrapSelectorWheel(false);
    
        pickerThermostatMinute = view.findViewById(R.id.pickerThermostatMinute);
        pickerThermostatMinute.setMinValue(0);
        pickerThermostatMinute.setMaxValue(59); // TODO set display values to always be 2 digits
        pickerThermostatMinute.setValue(timeNumbers[1]);
        pickerThermostatMinute.setWrapSelectorWheel(false);
        // reference on how to make picker be 2 digits: https://stackoverflow.com/questions/19737755/how-do-you-display-a-2-digit-numberpicker-in-android
        pickerThermostatMinute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
    
        pickerThermostatAMPM = view.findViewById(R.id.pickerThermostatAMPM);
        pickerThermostatAMPM.setMinValue(0);
        pickerThermostatAMPM.setMaxValue(1);
        pickerThermostatAMPM.setDisplayedValues(new String[]{"AM", "PM"});
        pickerThermostatAMPM.setValue(timeNumbers[2]);
        pickerThermostatAMPM.setWrapSelectorWheel(false);
        
        
        editTextThermostatTemperature = view.findViewById(R.id.editTextThermostatTemperature);
        editTextThermostatTemperature.setText(clickedTemperature);
    
    
        Button buttonCancelThermostatEntry = view.findViewById(R.id.buttonCancelThermostatEntry);
        buttonCancelThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Cancel Clicked");
                thermostatAddOrEditActivity.setResult(0); // unused. reset to 0
                thermostatAddOrEditActivity.finish();
            }
        });
    
        Button buttonSaveThermostatEntry = view.findViewById(R.id.buttonSaveThermostatEntry);
        buttonSaveThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Save Clicked");
                // TODO Check that Temperature field is filled. notify user and do not allow if null
                Intent saveIntent = createThermostatInputIntent();
                thermostatAddOrEditActivity.setResult(THERMOSTAT_RESULT_SAVE, saveIntent);
                thermostatAddOrEditActivity.finish();
            }
        });
    
        Button buttonDeleteThermostatEntry = view.findViewById(R.id.buttonDeleteThermostatEntry);
        buttonDeleteThermostatEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- -- Delete Clicked");
                Intent deleteIntent = createThermostatInputIntent();
                thermostatAddOrEditActivity.setResult(THERMOSTAT_RESULT_DELETE, deleteIntent);
                thermostatAddOrEditActivity.finish();
            }
        });
    
        TextView addOrEditTitle = view.findViewById(R.id.textThermostatAddOrEditTitle);
        if (inAdd) {
            // Edit and Add share the same Activity
            // switch the title to Add Entry and hide the Delete Button
            addOrEditTitle.setText(R.string.thermostatAddTitle);
            buttonDeleteThermostatEntry.setVisibility(View.INVISIBLE);
        }
        
        return view;
    }
    
    public void setParent(ThermostatAddOrEditActivity parent) {
        thermostatAddOrEditActivity = parent;
    }
    
    private Intent createThermostatInputIntent() {
        // reference on how to send data with result: https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
        Intent resultIntent = new Intent();
        
        resultIntent.putExtra("ID", clickedItemID);
        resultIntent.putExtra("Day", convertNumberToStringDay(pickerThermostatDay.getValue()));
        int[] timeNumbers = new int[3];
        timeNumbers[0] = pickerThermostatHour.getValue();
        timeNumbers[1] = pickerThermostatMinute.getValue();
        timeNumbers[2] = pickerThermostatAMPM.getValue();
        resultIntent.putExtra("Time", convertNumbersToStringTime(timeNumbers));
        if (editTextThermostatTemperature.getText().toString().equals(""))
            resultIntent.putExtra("Temperature", "0"); // TODO remove this when it is impossible to accept blank temperatures
        else
            resultIntent.putExtra("Temperature", editTextThermostatTemperature.getText().toString());
        
        return resultIntent;
    }
    
    
    private int convertStringToNumberDay(String dayString) {
        Log.i(ACTIVITY_NAME, "-- In convertStringToNumberDay. Converting: " + dayString);
        for (int i = 0; i < daysOfTheWeek.length; i++)
            if (dayString.equals(daysOfTheWeek[i]))
                return i;
        
        return 0; // made it through without a match, return 0 by default
    }
    
    private String convertNumberToStringDay(int dayNumber) {
        Log.i(ACTIVITY_NAME, "-- In convertNumberToStringDay. Converting: " + dayNumber);
        return daysOfTheWeek[dayNumber]; // can be 0-6, "Sunday", ..., "Sat.."
    }
    
    
    private int[] convertStringToNumbersTime(String timeString) {
        Log.i(ACTIVITY_NAME, "-- In convertStringToNumbersTime. Converting: " + timeString);
        // String is in the form "11:11AM" or " 1:01AM"
        
        String[] timeStringSplit = timeString.split(":");
        
        String hourString = timeStringSplit[0];
        hourString = hourString.replaceAll(" ", ""); // will remove space if 1-9 (if any)
        
        String minuteString = "" + timeStringSplit[1].charAt(0) + timeStringSplit[1].charAt(1);
        
        String AMPMString = "" + timeString.charAt(timeString.length() - 2) + timeString.charAt(timeString.length() - 1);
        
        int[] timeNumbers = new int[3]; // hours, minutes, AMPM
        timeNumbers[0] = Integer.parseInt(hourString);
        timeNumbers[1] = Integer.parseInt(minuteString);
        timeNumbers[2] = convertStringToNumberAMPM(AMPMString);
        
        return timeNumbers;
    }
    
    private String convertNumbersToStringTime(int[] timeNumbers) {
        Log.i(ACTIVITY_NAME, "-- In convertNumbersToStringTime. Converting: " + timeNumbers[0] + ", " + timeNumbers[1] + ", " + timeNumbers[2]);
        String timeString = "";
        
        if (timeNumbers[0] < 10)
            timeString += " "; // add space before single digit number
        timeString += timeNumbers[0] + ":";
        
        if (timeNumbers[1] < 10)
            timeString += "0"; // add 0 before single digit number
        timeString += timeNumbers[1];
        
        timeString += convertNumberToStringAMPM(timeNumbers[2]);
        
        return timeString;
    }
    
    
    private int convertStringToNumberAMPM(String AMPMString) {
        Log.i(ACTIVITY_NAME, "-- In convertStringToNumberAMPM. Converting: " + AMPMString);
        if (AMPMString.charAt(0) == 'P')
            return 1;
        else
            return 0;
    }
    
    private String convertNumberToStringAMPM(int AMPMNumber) {
        Log.i(ACTIVITY_NAME, "-- In convertNumberToStringAMPM. Converting: " + AMPMNumber);
        if (AMPMNumber == 0)
            return "AM";
        else
            return "PM";
    }
}




