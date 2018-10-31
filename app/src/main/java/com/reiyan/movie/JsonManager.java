package com.reiyan.movie;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.reiyan.movie.adapter.RecAdapter;
import com.reiyan.movie.data.MovieData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class  JsonManager extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String url, json, id, title, thumbnail, description, popularity, releaseDate, rate;
    private RecyclerView recyclerView;
    private List<MovieData> movies = new ArrayList<>();
    HttpHandler handler;
    JSONArray array;
    JSONObject jObject;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public JsonManager(Context context, String url, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.url = url;
        this.recyclerView = recyclerView;
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        handler = new HttpHandler();
        json = handler.makeService(url);
        if (json != null ){
            try {
                jObject = new JSONObject(json);
                array = jObject.getJSONArray("results");
                for (int i = 0; i <array.length() ; i++) {
                    JSONObject object = array.getJSONObject(i);
                    id = object.getString("id");
                    title = object.getString("title");
                    thumbnail = "http://image.tmdb.org/t/p/w185" + object.getString("poster_path");
                    description = object.getString("overview");
                    popularity = object.getString("popularity");
                    releaseDate = object.getString("release_date");
                    rate = object.getString("vote_average");
                    movies.add(new MovieData(id, title, thumbnail, description, popularity, releaseDate, rate));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mSwipeRefreshLayout.setRefreshing(false);
        RecAdapter adapter = new RecAdapter(context, movies);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,hitungUkuranLayar(2)));
    }

    private int hitungUkuranLayar(int size_semula){
        int size = size_semula;
        String ukuran = ambilCategoryUkuranLayar();

        if (ukuran.equals("small")){
            if (size != 1){
                size -= 1;
            }
        } else if (ukuran.equals("normal")){
        } else if (ukuran.equals("large")){
            size += 2;
        } else if (ukuran.equals("xlarge")){
            size += 3;
        }

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            size = (int) (size * 1.5);
        }
            return size;
    }

    private String ambilCategoryUkuranLayar() {
        int layoutLayar = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (layoutLayar){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return "xlarge";
            default:
                return "unknwon";
        }
    }

}
