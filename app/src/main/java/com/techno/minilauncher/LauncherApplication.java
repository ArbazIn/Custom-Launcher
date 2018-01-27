package com.techno.minilauncher;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by thetaubuntu5 on 14/12/17.
 */

public class LauncherApplication extends Application {
    public static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }
}
