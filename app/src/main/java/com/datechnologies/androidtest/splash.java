package com.datechnologies.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by saifjame on 1/14/19.
 */


public class splash extends AppCompatActivity {
    private static int time= 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent transition = new Intent(splash.this, MainActivity.class);
                startActivity(transition);
                finish();
            }
        },time);
    }
}