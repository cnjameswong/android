package com.yw.librouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class YwRouter {
    private static YwRouter router = new YwRouter();
    private Map<String, Class<? extends Activity>> map;

    private YwRouter() {
        map = new HashMap<>();
    }

    public static YwRouter getInstance() {
        return router;
    }

    private Context context;
    public void init(Context context) {
        this.context = context.getApplicationContext();
        List<String> classNames = getClassName("com.yw.router.path.init");
        for (String className : classNames) {
            try {
                Object o = Class.forName(className).newInstance();
                if (o instanceof IActivityRouter) {
                    ((IActivityRouter) o).addActivity(map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private List<String> getClassName(String packageName) {
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
            DexFile dexFile = new DexFile(path);
            Enumeration entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String name = (String) entries.nextElement();
                if (name.contains(packageName)) {
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }

    public void navigation(Context context, String uri) {
        if (map.containsKey(uri)) {
            Intent i = new Intent();
            i.setClass(context, map.get(uri));
            if (!(context instanceof Activity)) {
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(i);
        } else {
            //log
            Log.d("wangyanpeng", "uri not exist");
        }
    }

}
