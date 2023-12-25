package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class transport extends AppCompatActivity {
    ImageView trans;
    CardView businfo,railway,cab,rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        getSupportActionBar().hide();
        trans=findViewById(R.id.transback);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transport.this,MainActivity.class));finish();
            }
        });
        businfo=findViewById(R.id.bus);
        businfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transport.this, businfo.class));finish();
            }
        });
        railway=findViewById(R.id.traint);
        railway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transport.this, train.class));
                finish();
            }
        });
        cab=findViewById(R.id.cab);
        rent=findViewById(R.id.rent);
        cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transport.this, cab.class));finish();
            }
        });
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(transport.this, rent.class));
                finish();
            }
        });

    }
}