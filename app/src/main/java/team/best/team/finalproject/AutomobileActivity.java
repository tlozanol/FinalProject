package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AutomobileActivity extends Activity {

    protected static final String ACTIVITY_NAME = "AutomobileActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);
    }

    Button seeHistoryDB = findViewById(R.id.seeHistory);


}
