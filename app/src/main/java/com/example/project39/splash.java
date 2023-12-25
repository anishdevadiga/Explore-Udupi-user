package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class splash extends AppCompatActivity {
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent loginintent=new Intent(splash.this,login.class);

                startActivity(loginintent);
                finish();
            }
        },3000);

        //gif implementation
        splash=findViewById(R.id.splashgif);

        Glide.with(this).asGif().load(R.raw.newsplash).into(splash);
    }
}