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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class tourist extends AppCompatActivity {

    ImageView dashback;
    RecyclerView recyclerView;
    TourAdapter tourAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2979FF")));
        getSupportActionBar().setTitle("");

        dashback=findViewById(R.id.tourback);
        dashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashtour=new Intent(tourist.this,MainActivity.class);
                startActivity(dashtour);
                finish();

            }
        });
        recyclerView=findViewById(R.id.recycletour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Tourmodel> options =
                new FirebaseRecyclerOptions.Builder<Tourmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tourist_places"), Tourmodel.class)
                        .build();
        tourAdapter=new TourAdapter(options);
        recyclerView.setAdapter(tourAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        tourAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tourAdapter.stopListening();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
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
    private void txtSearch(String str){
        FirebaseRecyclerOptions<Tourmodel> options =
                new FirebaseRecyclerOptions.Builder<Tourmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tourist_places").orderByChild("placename").startAt(str).endAt(str+"~"), Tourmodel.class)
                        .build();
        tourAdapter=new TourAdapter(options);
        tourAdapter.startListening();
        recyclerView.setAdapter(tourAdapter);


    }
}