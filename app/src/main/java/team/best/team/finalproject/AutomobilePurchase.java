package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AutomobilePurchase extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobilePurchase";

    private MyEditTextDatePicker autoDatePicker;
    private Spinner spinner;
    private EditText gas;
    private EditText price;
    private EditText liters;
    private EditText avgKm;
    private Button savePurchase;

    Snackbar autoSnackBar;

    ArrayList<String> automobileInputArray = new ArrayList<>();

    DatabaseHelper dbAutomobile;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_purchase);

        dbAutomobile = new DatabaseHelper(this);
        database = dbAutomobile.getWritableDatabase();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listOfCars, android.R.layout.simple_spinner_item);
        spinner = findViewById(R.id.carText);
        spinner.setAdapter(adapter);

        autoDatePicker = new MyEditTextDatePicker(this, R.id.carDateText);
        gas = findViewById(R.id.gasText);
        price = findViewById(R.id.priceText);
        liters = findViewById(R.id.litersText);
        avgKm = findViewById(R.id.avgKmsText);

        savePurchase = findViewById(R.id.saveButton);
        savePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- User clicked Save Purchase");

               //Save information to the data base


                autoSnackBar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.automobileSnackbar, Snackbar.LENGTH_SHORT);
                //autoSnackBar.setAction(null, new AutomobilePurchase());
                //autoSnackBar.setAction(R.string.undo_string, )
                autoSnackBar.show();

               // Intent intent = new Intent(AutomobilePurchase.this, AutomobileActivity.class);
              //  startActivity(intent);


            }
        });

        Button seeHistoryDB = findViewById(R.id.seeHistory);
        seeHistoryDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(ACTIVITY_NAME, "-- User clicked History");
                Intent intent = new Intent(AutomobilePurchase.this, Automobile_History.class);
                startActivity(intent);
            }
        });
    }
}
