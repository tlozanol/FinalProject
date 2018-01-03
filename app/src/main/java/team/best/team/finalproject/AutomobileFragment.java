package team.best.team.finalproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class AutomobileFragment extends Activity {

    private static final String ACTIVITY_NAME = "In AutomobileFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_fragment);

        launchInputFragment();
    }

    private void launchInputFragment(){
        Log.i(ACTIVITY_NAME, "-- In launchInputFragment()");

        Bundle entryBundle = getIntent().getExtras();

        AutomobilePurchase autoPurchase = new AutomobilePurchase();
        autoPurchase.setParent(AutomobileFragment.this);
        autoPurchase.setArguments(entryBundle);

        FragmentManager autoManager = getFragmentManager();
        FragmentTransaction autoFragTransaction = autoManager.beginTransaction();
        autoFragTransaction.replace(R.id.frameLoadingBar, autoPurchase);
        autoFragTransaction.commit();
    }
}
