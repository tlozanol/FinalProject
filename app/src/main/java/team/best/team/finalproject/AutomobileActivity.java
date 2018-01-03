package team.best.team.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AutomobileActivity extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobileActivity";

    private int AUTOMOBILE_REQUEST_ADD = 10;
    private int AUTOMOBILE_REQUEST_EDIT = 20;
    private int AUTOMOBILE_RESULT_SAVE = 100;
    private int AUTOMOBILE_RESULT_DELETE = 200;

    ArrayList<ArrayList<String>> automobileArray = new ArrayList<>();
    ArrayList<String> deletedEntry = new ArrayList<>();
    ListView automobileList;
    AutomobileListAdapter automobileListAdapter;
    int positionClicked;

    DatabaseHelper dataBaseHelper;
    SQLiteDatabase writableDataBase;
    RelativeLayout automobileRelativeLayout;
    Button addGasPurchase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);
        Log.i(ACTIVITY_NAME, "-- In onCreate");

        automobileRelativeLayout = findViewById(R.id.automobileRelativeLayout);

        dataBaseHelper = new DatabaseHelper(this);
        writableDataBase = dataBaseHelper.getWritableDatabase();
        automobileArray = dataBaseHelper.getAutomobileDBData();

        automobileList = findViewById(R.id.listAutomobilePurchase);
        automobileListAdapter = new AutomobileListAdapter(this);
        automobileList.setAdapter(automobileListAdapter);
        automobileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long position) {
                positionClicked = (int) position;
                Bundle automobileAddPurchaseBundle = createAutomobileEditBundle((int) position);
                Intent editGasPurchase = new Intent(AutomobileActivity.this, AutomobileFragment.class);
                editGasPurchase.putExtras(automobileAddPurchaseBundle);
                startActivityForResult(editGasPurchase, AUTOMOBILE_REQUEST_EDIT);
            }
        });

        addGasPurchase = findViewById(R.id.buttonAddGasPurchase);
        addGasPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "--Add Gas Purchase");

                Bundle automobileAddPurchaseBundle = createAutomobileAddBundle();
                Intent addGasPurchase = new Intent(AutomobileActivity.this, AutomobileFragment.class);
                addGasPurchase.putExtras(automobileAddPurchaseBundle);
                startActivityForResult(addGasPurchase, AUTOMOBILE_REQUEST_ADD);
            }
        });

        Button aboutButton = findViewById(R.id.automobileAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutomobileActivity.this, Automobile_about.class);
                startActivity(intent);
            }
        });
    }

    private Bundle createAutomobileAddBundle(){

        Bundle automobileEntryBundle = new Bundle();
        automobileEntryBundle.putBoolean("InAdd", true);
        automobileEntryBundle.putInt("ID", -1);
        automobileEntryBundle.putString("Date", " ");
        automobileEntryBundle.putString("Type", "");
        automobileEntryBundle.putString("Gas", "");
        automobileEntryBundle.putString("Price", "");
        automobileEntryBundle.putString("Liters", "");
        automobileEntryBundle.putString("AvgKm", "");

        return automobileEntryBundle;
    }

    private Bundle createAutomobileEditBundle(int position){

        int clickedID = dataBaseHelper.getAutomobileItemID(position);
        String clickedDate = automobileArray.get(position).get(1);
        String clickedCar = automobileArray.get(position).get(2);
        String clickedType = automobileArray.get(position).get(3);
        String clickedPrice = automobileArray.get(position).get(4);
        String clickedLiters = automobileArray.get(position).get(5);
        String clickedAvgKm = automobileArray.get(position).get(6);

        Bundle automobileEntryBundle = new Bundle();
        automobileEntryBundle.putBoolean("InAdd", false);
        automobileEntryBundle.putInt("ID", clickedID);
        automobileEntryBundle.putString("Date", clickedDate);
        automobileEntryBundle.putString("Type", clickedCar);
        automobileEntryBundle.putString("Gas", clickedType);
        automobileEntryBundle.putString("Price", clickedPrice);
        automobileEntryBundle.putString("Liters", clickedLiters);
        automobileEntryBundle.putString("AvgKm", clickedAvgKm);

        return automobileEntryBundle;
    }

    @Override
    protected void onActivityResult(int codeRequest, int resultCode, Intent data){
        super.onActivityResult(codeRequest, resultCode, data);
        Log.i(ACTIVITY_NAME, "-- In onActivityResult()");

        if(codeRequest == AUTOMOBILE_REQUEST_ADD){
            if(resultCode == AUTOMOBILE_RESULT_SAVE){

                Log.i(ACTIVITY_NAME, "-- -- Returned from Add: Save");
                showSnackBar(getResources().getString(R.string.automobileSnackbar));

                String newDate = data.getStringExtra("Date");
                String newType = data.getStringExtra("Type");
                String newGas = data.getStringExtra("Gas");
                String newPrice = data.getStringExtra("Price");
                String newLiters = data.getStringExtra("Liters");
                String newAvgKm = data.getStringExtra("AvgKm");

                ArrayList<String> addToDB = new ArrayList<>();
                addToDB.add(newDate);
                addToDB.add(newType);
                addToDB.add(newGas);
                addToDB.add(newPrice);
                addToDB.add(newLiters);
                addToDB.add(newAvgKm);

                dataBaseHelper.addAutomobileDBEntry(addToDB);
            }
        } else if (codeRequest == AUTOMOBILE_REQUEST_EDIT) {
            if (resultCode == AUTOMOBILE_RESULT_SAVE) {
                Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Save");
                showToast(getResources().getString(R.string.automobileToast));

                int IDToUpdate = data.getIntExtra("ID", 1);
                String newDate = data.getStringExtra("Date");
                String newCar = data.getStringExtra("Type");
                String newType = data.getStringExtra("Gas");
                String newPrice = data.getStringExtra("Price");
                String newLiters = data.getStringExtra("Liters");
                String newAvgKm = data.getStringExtra("AvgKm");

                ArrayList<String> updateDB = new ArrayList<>();
                updateDB.add(newDate);
                updateDB.add(newCar);
                updateDB.add(newType);
                updateDB.add(newPrice);
                updateDB.add(newLiters);
                updateDB.add(newAvgKm);

                dataBaseHelper.updateAutomobileDBEntry(IDToUpdate, updateDB);
            }
            if (resultCode == AUTOMOBILE_RESULT_DELETE) {
                Log.i(ACTIVITY_NAME, "-- -- Returned from Edit: Delete");

                int IDDeleteData = data.getIntExtra("ID", 0);

                deletedEntry = automobileArray.get(positionClicked);
                deletedEntry.remove(0);
                showToast(getResources().getString(R.string.automobileToast2));

                dataBaseHelper.deleteAutomobileDBEntry(IDDeleteData);
            }
        }
    }

    private void refreshAutomobileEntries(){
        Log.i(ACTIVITY_NAME, "-- In refreshAutomobileEntries()");

        dataBaseHelper = new DatabaseHelper(this);
        writableDataBase = dataBaseHelper.getWritableDatabase();
        automobileArray = dataBaseHelper.getAutomobileDBData();

        automobileListAdapter = new AutomobileListAdapter(this);
        automobileList.setAdapter(automobileListAdapter);
    }

    private void showSnackBar(String message){
        Log.i(ACTIVITY_NAME, "-- In showSnackBar()");

        Snackbar autoSnackBar = Snackbar.make(automobileRelativeLayout, message, Snackbar.LENGTH_LONG);
        autoSnackBar.show();
    }

    private void showToast(String message){
        Log.i(ACTIVITY_NAME, "-- In showToast()");

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    private class AutomobileListAdapter extends ArrayAdapter<String>{

        public AutomobileListAdapter(Context ctx){
            super(ctx, 0);
            Log.i(ACTIVITY_NAME, "-- AutomobileListAdapter constructor");
        }

        public int getCount() {
           return automobileArray.size();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = AutomobileActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.automobile_entry, null);

            TextView  AutomobileEntryDate = result.findViewById(R.id.AutomobileEntryDate);
            TextView  AutomobileEntryType = result.findViewById(R.id.AutomobileEntryType);
            TextView  AutomobileEntryGas = result.findViewById(R.id.AutomobileEntryGas);

            ArrayList<String> row = automobileArray.get(position);

            AutomobileEntryDate.setText(row.get(1));
            AutomobileEntryType.setText(row.get(2));
            AutomobileEntryGas.setText(row.get(3));

            return result;
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(ACTIVITY_NAME, "-- In onResume()");

        refreshAutomobileEntries();
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
