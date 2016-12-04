package com.enihsyou.shane.sunset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class SunsetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunset);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.activity_sunset);
        if (fragment == null) {
            fragment = SunsetFragment.newInstance();
            manager.beginTransaction().add(R.id.activity_sunset, fragment).commit();
        }
    }
}
