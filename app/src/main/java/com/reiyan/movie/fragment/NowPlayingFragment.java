package com.reiyan.movie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reiyan.movie.JsonManager;
import com.reiyan.movie.R;
import com.reiyan.movie.adapter.RecAdapter;
import com.reiyan.movie.data.MovieData;

import java.util.ArrayList;
import java.util.List;


public class NowPlayingFragment extends Fragment {

    RecyclerView recyclerView;
    String url;
    SwipeRefreshLayout swipe;
    public NowPlayingFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = v.findViewById(R.id.recView);
        swipe = v.findViewById(R.id.swipe);
        url = "https://api.themoviedb.org/3/movie/now_playing?api_key=05d24d1094bc376832434c74ca08824f&language=en-US";
        swipe.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange));
        new JsonManager(getActivity(), url, recyclerView,swipe ).execute();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JsonManager(getActivity(), url, recyclerView, swipe).execute();
            }
        });
        return v;
    }



}
