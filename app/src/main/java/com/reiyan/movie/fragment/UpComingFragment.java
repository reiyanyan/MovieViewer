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



/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    SwipeRefreshLayout swipe;
    String url;
    RecyclerView recyclerView;


    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        swipe = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.recView);
        url = "https://api.themoviedb.org/3/movie/upcoming?api_key=05d24d1094bc376832434c74ca08824f&language=en-US";
        swipe.setColorSchemeColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.green), getResources().getColor(R.color.orange));
        new JsonManager(getActivity(), url, recyclerView,swipe).execute();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JsonManager(getActivity(), url, recyclerView, swipe).execute();
            }
        });
        return view;
    }

}
