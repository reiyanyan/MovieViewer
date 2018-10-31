package com.reiyan.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.reiyan.movie.adapter.RecAdapter;
import com.reiyan.movie.data.MovieData;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    RecAdapter adapter;
    Toolbar toolbar;
    List<MovieData> data;
    SQLITE db;
    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        toolbar = findViewById(R.id.toolbar);
        data = new ArrayList<>();
        recView =  findViewById(R.id.recView);
        db = new SQLITE(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data.addAll(db.readAll());
        adapter = new RecAdapter(this, data);

        recView.setLayoutManager(new GridLayoutManager(this, 2));
        recView.setAdapter(adapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
