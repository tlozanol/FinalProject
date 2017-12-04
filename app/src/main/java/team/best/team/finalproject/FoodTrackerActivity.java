package team.best.team.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FoodTrackerActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        
        Button btnFoodTracker = (Button) findViewById(R.id.btnFoodTrackerEnter);
        EditText editTextFoodTracker = (EditText) findViewById(R.id.editTextFoodTracker);

        ListView ListFood = (ListView) findViewById(R.id.listFood);
        String[] ItemMenu = {"Apple", "Banana"};

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ItemMenu);
        ListFood.setAdapter(listViewAdapter);

        ListFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if(position==0){
                    Intent intent = new Intent(view.getContext(), FoodTrAppleInfoActivity.class);
                    startActivity(intent);
                }

                if(position==1){
                    Intent intent = new Intent(view.getContext(), FoodTrBananaInfoActivity.class);
                    startActivity(intent);
                }

            }
        });



        //Button buttonStartListItems = findViewById(R.id.button);
       // buttonStartListItems.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v){
        //        Log.i(ACTIVITY_NAME, "User clicked Start List Items");
        //        Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
        //       startActivityForResult(intent, 10);
         //   }
        //});




        

    }
}
