package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Automobile_about extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_about);

        Button backButton = findViewById(R.id.backButtonAuto);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Automobile_about.this, AutomobileActivity.class);
                startActivity(intent);
            }
        });

    }
}
