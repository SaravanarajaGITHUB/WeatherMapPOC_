package com.saravana.weathermappoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.saravana.weathermappoc.util.AppConstants;
import com.saravana.weathermappoc.util.AppDataLog;
import com.saravana.weathermappoc.util.ObjectStore;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        try {
            final HomeController homeController = new HomeController();
            ObjectStore.I().putObject(AppConstants.OBJ_HOME_CONTROLLER, homeController);
            homeController.startHomeActivity(this);
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }

    }


    class temp {


    }
}
