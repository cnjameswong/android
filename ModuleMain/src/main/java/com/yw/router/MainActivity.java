package com.yw.router;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yw.libannotation.Route;
import com.yw.librouter.YwRouter;

@Route(path="/main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToUser(View view) {
//        Intent i = new Intent(this, UserActivity.class);
//        startActivity(i);
        YwRouter.getInstance().navigation(this, "/user/user");
    }
}