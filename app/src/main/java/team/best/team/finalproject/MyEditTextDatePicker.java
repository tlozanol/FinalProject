package team.best.team.finalproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * As found on stackoverflow --> https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
 * credit: SatanEnglish
 */

public class MyEditTextDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener
{
    private EditText editTextDate;
    private int day;
    private int month;
    private int year;
    private StringBuilder strDate;
    private Context context;

    String getDate(){ return strDate.toString();}

    boolean isSet(){ return editTextDate.getText().toString().trim().length() != 0; }

    MyEditTextDatePicker(Context context, int editTextViewID)
    {
        Activity activity = (Activity)context;
        this.editTextDate = activity.findViewById(editTextViewID);
        this.editTextDate.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        updateDisplay();
    }

    @Override
    public void onClick(View view)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context,
                this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void updateDisplay()
    {
        String strMonth = "";

        switch(month)
        {
            case 0:
                strMonth = "Jan";
                break;
            case 1:
                strMonth = "Feb";
                break;
            case 2:
                strMonth = "Mar";
                break;
            case 3:
                strMonth = "Apr";
                break;
            case 4:
                strMonth = "May";
                break;
            case 5:
                strMonth = "Jun";
                break;
            case 6:
                strMonth = "Jul";
                break;
            case 7:
                strMonth = "Aug";
                break;
            case 8:
                strMonth = "Sept";
                break;
            case 9:
                strMonth = "Oct";
                break;
            case 10:
                strMonth = "Nov";
                break;
            case 11:
                strMonth = "Dec";
                break;
            default:
                break;
        }

        strDate = new StringBuilder().append(strMonth).append("/").append(day).append("/").append(year);

        editTextDate.setText(strDate);
    }

    void resetDisplay()
    {
        editTextDate.setText(R.string.activityDateHint);
    }
}
