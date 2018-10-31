package com.reiyan.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reiyan.movie.data.MovieData;

public class DetailActivity extends AppCompatActivity {

    TextView title, rating, popularity, date, desc;
    ImageView thumbnail;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.title);
        rating = findViewById(R.id.rate);
        popularity = findViewById(R.id.popularity);
        date = findViewById(R.id.releaseDate);
        desc = findViewById(R.id.desc);
        thumbnail = findViewById(R.id.thumbnail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieData data = getIntent().getParcelableExtra("parcel");
        if (data != null) {
            title.setText(data.getTitle());
            rating.setText(data.getRate());
            popularity.setText(data.getPopularity());
            date.setText(data.getReleaseDate());
            desc.setText(data.getDescription());
            Glide.with(this).load(data.getThumbnail()).into(thumbnail);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
            case R.id.share:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "https://www.reiyan.com/movie");
                i.setType("text/plain");
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
