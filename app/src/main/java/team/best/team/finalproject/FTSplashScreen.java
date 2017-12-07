package team.best.team.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class FTSplashScreen extends Activity {
    private static final String ACTIVITY_NAME = "FTSplashScreen";
    private ProgressBar myProgressBar;
    private int myProgressStatus = 0;
    private Handler myHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myProgressBar = (ProgressBar) findViewById(R.id.progressbar);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (myProgressStatus<100){
                    myProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            myProgressBar.setProgress(myProgressStatus);
                        }
                    });
                }


            }
        }).start();





    }
}
