package com.diy.travelers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.share.Share;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(Constants.LOGIN_SHARED_PREFRENCE, MODE_PRIVATE);

        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);

                    // After 5 seconds redirect to another intent

                    if (!sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)) {
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(i);
                    } else if (sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, true)) {
                        Intent i = new Intent(getBaseContext(), Dashboard.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(i);
                    }

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
