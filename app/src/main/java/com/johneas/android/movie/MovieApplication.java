package com.johneas.android.movie;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

public class MovieApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (BuildConfig.DEBUG) {

            //Init stheto
            Stetho.initializeWithDefaults(this);

            if (this.getResources().getBoolean(R.bool.support_leak_canary)) {
                // Code to catch leaks
                if (LeakCanary.isInAnalyzerProcess(this)) {
                    // This process is dedicated to LeakCanary for heap analysis.
                    // You should not init your app in this process.
                    return;
                }
                LeakCanary.install(this);
            }
        }
    }
}
