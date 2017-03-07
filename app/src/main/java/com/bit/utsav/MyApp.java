package com.bit.utsav;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by PC on 2/29/2016.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
