package com.lazydeveloper.nanopad.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lazydeveloper.nanopad.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation bottomAnimation;
    TextView txtTitle;
    Boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash_screen);

        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        txtTitle = findViewById(R.id.SplashTitle);

        txtTitle.setAnimation(bottomAnimation);

        new Handler().postDelayed(() ->
        {
            Intent intent1 = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            startActivity(intent1);
            finish();

            /*SharedPreferences sh = getSharedPreferences("OnBoarding",0);
            bool = sh.getBoolean("Boolean",false);
            if (bool)
            {
//                Intent intent1 = new Intent(SplashScreenActivity.this, MainActivity.class);
                Intent intent1 = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(intent1);
                finish();
            }else
            {
                Intent intent2 = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                startActivity(intent2);
                finish();
            }*/
        },2500);
    }
}