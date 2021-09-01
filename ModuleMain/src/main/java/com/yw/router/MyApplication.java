package com.yw.router;

import android.app.Application;

import com.yw.librouter.YwRouter;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YwRouter.getInstance().init(this);
    }
}
