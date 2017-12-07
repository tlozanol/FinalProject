package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AutomobilePurchase extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobilePurchase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_purchase);

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
