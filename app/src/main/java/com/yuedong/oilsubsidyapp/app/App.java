package com.yuedong.oilsubsidyapp.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/11/24.
 */
public class App extends Application {
    private static App instance;
    private Context context;

    public static App getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }
}
