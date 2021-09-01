package com.yw.librouter;

import android.app.Activity;

import java.util.Map;

public interface IActivityRouter {
    public void addActivity(Map<String, Class<? extends Activity>> paths) ;
}
