package com.yw.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yw.libannotation.Route;

@Route(path="/user/user")
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}