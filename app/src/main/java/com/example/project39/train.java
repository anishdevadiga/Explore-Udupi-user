package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class train extends AppCompatActivity {
    Button railloc,railbook;
    ImageView backtrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        railloc=findViewById(R.id.trainlocation);
        getSupportActionBar().hide();
        railloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/BYAzQTk7A3DbqHok6"));
                startActivity(i);



            }
        });
        railbook=findViewById(R.id.btntrainbook);
        railbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.irctc.co.in/nget/train-search"));
                startActivity(i1);


            }
        });

        backtrain=findViewById(R.id.trainback);
        backtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(train.this,transport.class));
                finish();
            }
        });

    }
}