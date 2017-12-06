package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AutomobileActivity extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobileActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);

        Button addGasPurchase = findViewById(R.id.buttonAddGasPurchase);
        addGasPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "--Add Gas Purchase");
                Intent intent = new Intent(AutomobileActivity.this, AutomobilePurchase.class);
                startActivity(intent);
            }
        });

        ListView automobileList = (ListView) findViewById(R.id.listAutomobileGasPurchase);
        String[] autoArrayList = {"purchase1"};

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autoArrayList);
        automobileList.setAdapter(listViewAdapter);

        automobileList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(view.getContext(), Automobile_History.class);
                    startActivity(intent);
                }
            }

        });
    }




}
