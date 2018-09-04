package com.saravana.weathermappoc.util.network;

import com.saravana.weathermappoc.util.AppDataLog;

import java.util.Observable;

public class InternetConnectionDetector extends Observable {
    private static final InternetConnectionDetector ourInstance = new InternetConnectionDetector();

    public static InternetConnectionDetector getInstance() {
        return ourInstance;
    }

    private InternetConnectionDetector() {
    }

    public boolean bIsInternetConnected = false;
    public boolean bIsShowSnackBar = true; //show custom snack bar to user until he get alerted

    public void setInternetConnection(boolean bIsInternetConnected_in, String stClass_in) {
        try {
            if (bIsInternetConnected_in != bIsInternetConnected) {
                bIsInternetConnected = bIsInternetConnected_in;

                //change snack bar state
                if (bIsInternetConnected_in) {
                    bIsShowSnackBar = false; // don't show offline snack bar
                } else {
                    bIsShowSnackBar = true; // show offline snack bar
                }
                setChanged();
                notifyObservers();
            }
        } catch (Exception e) {
            AppDataLog.TPostExep(e);
        }
    }
}
