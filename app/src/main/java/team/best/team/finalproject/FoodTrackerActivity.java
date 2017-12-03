package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
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
        String[] ItemMenu = {"Apple", "Banana", "Carrot", "Celeri", "Grapes", "Mango", "Orange"};
        
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ItemMenu);
        ListFood.setAdapter(listViewAdapter);
        
        
    }
}
