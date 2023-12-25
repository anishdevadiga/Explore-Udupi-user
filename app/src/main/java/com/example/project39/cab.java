package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class cab extends AppCompatActivity {
    ImageView cabback;
    RecyclerView cabrecycle;
    CabAdapter cabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");
        cabback=findViewById(R.id.cabback);
        cabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cab.this,transport.class));
                finish();
            }
        });
        cabrecycle=findViewById(R.id.recyclecab);
        cabrecycle.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<CabModel> options=new FirebaseRecyclerOptions.Builder<CabModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cab"),CabModel.class).build();
        cabAdapter=new CabAdapter(options);
        cabrecycle.setAdapter(cabAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        cabAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cabAdapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str) {
        FirebaseRecyclerOptions<CabModel> options =
                new FirebaseRecyclerOptions.Builder<CabModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cab").orderByChild("cabname").startAt(str).endAt(str + "~"),CabModel.class)
                        .build();
        cabAdapter=new CabAdapter(options);
        cabrecycle.setAdapter(cabAdapter);
        cabAdapter.startListening();
    }
}