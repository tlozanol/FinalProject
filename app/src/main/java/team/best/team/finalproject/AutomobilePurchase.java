package team.best.team.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AutomobilePurchase extends Fragment {


    protected static final String ACTIVITY_NAME = "AutomobilePurchase";

    private int AUTOMOBILE_RESULT_SAVE = 100;
    private int AUTOMOBILE_RESULT_DELETE = 200;

    private AutomobileFragment autoloadBar;

    private int clickedID;
    private EditText datePurchase;
    private EditText carType;
    private EditText gas;
    private EditText price;
    private EditText liters;
    private EditText avgKm;
    private Button savePurchase;


    public AutomobilePurchase() {
        Log.i(ACTIVITY_NAME, "-- In constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "-- In onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(ACTIVITY_NAME, "-- In onCreateView");

        View view = inflater.inflate(R.layout.activity_automobile_purchase, null);

        final Bundle automobileEntryBundle = getArguments();
        boolean inAdd = automobileEntryBundle.getBoolean("InAdd", false);
        clickedID = automobileEntryBundle.getInt("ID", -1);
        String clickedDate = automobileEntryBundle.getString("Date", "");
        String clickedType = automobileEntryBundle.getString("Type", "");
        String clickedGas = automobileEntryBundle.getString("Gas", "");
        String clickedPrice = automobileEntryBundle.getString("Price", "");
        String clickedLiters = automobileEntryBundle.getString("Liters", "");
        String clickedAvgKm = automobileEntryBundle.getString("AvgKm", "");


        datePurchase = view.findViewById(R.id.carDateText);
        datePurchase.setText(clickedDate);
        carType = view.findViewById(R.id.carText);
        carType.setText(clickedType);
        gas = view.findViewById(R.id.gasText);
        gas.setText(clickedGas);
        price = view.findViewById(R.id.priceText);
        price.setText(clickedPrice);
        liters = view.findViewById(R.id.litersText);
        liters.setText(clickedLiters);
        avgKm = view.findViewById(R.id.avgKmsText);
        avgKm.setText(clickedAvgKm);

        if (!liters.getText().toString().equals("")) {
            Log.i(ACTIVITY_NAME, "-- Calculates average");
            avgKm.setText(calculateAverageKm());
        }


        savePurchase = view.findViewById(R.id.saveButton);
        savePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- User clicked Save Purchase");

                Intent saveIntent = createAutomobileInputIntent();
                autoloadBar.setResult(AUTOMOBILE_RESULT_SAVE, saveIntent);
                autoloadBar.finish();
            }
        });

        Button seeHistoryDB = view.findViewById(R.id.seeHistory);
        seeHistoryDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(ACTIVITY_NAME, "-- User clicked History");
                Intent intent = new Intent(AutomobilePurchase.this.getActivity(), Automobile_LoadingBar.class);
                startActivity(intent);
            }
        });

        Button deleteRecord = view.findViewById(R.id.deleteButton);
        deleteRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "-- User clicked Delete");
                Log.i(ACTIVITY_NAME, "-- Deleting: " + clickedID);

                Intent deleteintent = createAutomobileInputIntent();
                autoloadBar.setResult(AUTOMOBILE_RESULT_DELETE, deleteintent);
                autoloadBar.finish();


            }
        });

        if(inAdd){
            deleteRecord.setVisibility(View.INVISIBLE);
            seeHistoryDB.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    public void setParent(AutomobileFragment parent){
        autoloadBar = parent;
    }

    private Intent createAutomobileInputIntent(){
        Intent resultIntent = new Intent();

        resultIntent.putExtra("ID", clickedID);
        resultIntent.putExtra("Date", datePurchase.getText().toString());
        resultIntent.putExtra("Type", carType.getText().toString());
        resultIntent.putExtra("Gas", gas.getText().toString());
        resultIntent.putExtra("Price", price.getText().toString());
        resultIntent.putExtra("Liters", liters.getText().toString());
        resultIntent.putExtra("AvgKm", avgKm.getText().toString());

         return resultIntent;
    }

    private int convertStringToInt(String string){
        Log.i(ACTIVITY_NAME, "-- In convertStringToInt. Converting: " + string);

        int intConverted = Integer.parseInt(string);

        return intConverted;
    }

    private String calculateAverageKm (){
        Log.i(ACTIVITY_NAME, "-- In calculateAverageKm");

        int avgkm = convertStringToInt(liters.getText().toString());

        int finalValue = avgkm*8;

        return "" + finalValue;
    }
}
