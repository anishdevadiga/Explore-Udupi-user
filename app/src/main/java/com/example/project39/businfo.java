package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class businfo extends AppCompatActivity {

    ImageView backbus;
    TextView busum,bus2;


    Button busloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);
        getSupportActionBar().hide();
        backbus=findViewById(R.id.busback);
                backbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(businfo.this,transport.class));
                finish();
            }
        });
       busloc=findViewById(R.id.buslocation);
       busloc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/maps/doydWQjvfE7VZbyn9"));
               startActivity(i);

           }
       });
       busum=findViewById(R.id.busum);
       busum.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog logdial=new Dialog(businfo.this);
               logdial.setContentView(R.layout.bustimings);
               logdial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               logdial.show();
               logdial.findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       logdial.dismiss();
                   }
               });
           }
       });
        bus2=findViewById(R.id.bus2);
        bus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog log=new Dialog(businfo.this);
                log.setContentView(R.layout.bustimings1);
                log.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                log.show();
                log.findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        log.dismiss();
                    }
                });
            }
        });

    }
}