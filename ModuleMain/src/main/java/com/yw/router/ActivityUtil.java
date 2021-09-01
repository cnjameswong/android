package com.yw.router;
import android.app.Activity;
import com.yw.librouter.IActivityRouter;
import java.util.Map;
public class ActivityUtil implements IActivityRouter {
    public void addActivity(Map<String, Class<? extends Activity>> paths) {
        paths.put("/main/main", MainActivity.class);
    }
}
