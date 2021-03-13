package com.alan.heremovies;

import android.app.Application;

import com.alan.heremovies.sys.di.components.DaggerUtilComponent;
import com.alan.heremovies.sys.di.components.UtilComponent;
import com.alan.heremovies.sys.di.modules.ContextModule;

public class MainApp extends Application {

    private static UtilComponent utilComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        utilComponent = DaggerUtilComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public static UtilComponent getUtils() {
        return utilComponent;
    }

}
