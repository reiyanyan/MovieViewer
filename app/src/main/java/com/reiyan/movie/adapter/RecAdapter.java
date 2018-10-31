package com.reiyan.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.reiyan.movie.DetailActivity;
import com.reiyan.movie.R;
import com.reiyan.movie.SQLITE;
import com.reiyan.movie.data.MovieData;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.HolderHolder> {

    Context context;
    List<MovieData> listtData ;


    public RecAdapter(Context context, List<MovieData> listtData) {
        this.context = context;
        this.listtData = listtData;
    }

    public class HolderHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title, rate;
        ToggleButton favT;

        public HolderHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.txtRate);
            thumbnail = itemView.findViewById(R.id.img);
            favT = itemView.findViewById(R.id.favToggle);

        }
    }


    @NonNull
    @Override
    public HolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.blue, parent, false);
        return new HolderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderHolder holder, int position) {

        String id = listtData.get(position).getId();
        String title = listtData.get(position).getTitle();
        String thumbnail = listtData.get(position).getThumbnail();
        String desc = listtData.get(position).getDescription();
        String popularity = listtData.get(position).getPopularity();
        String date = listtData.get(position).getReleaseDate();
        String rate = listtData.get(position).getRate();

        final MovieData mo = new MovieData(id, title, thumbnail, desc, popularity, date, rate);

        final MovieData data = listtData.get(position);
        holder.title.setText(data.getTitle());
        holder.rate.setText(data.getRate());
        Glide.with(context).load(data.getThumbnail()).into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieData parc = new MovieData();
                Intent i = new Intent(context, DetailActivity.class);
                parc.setTitle(data.getTitle());
                parc.setPopularity(data.getPopularity());
                parc.setDescription(data.getDescription());
                parc.setRate(data.getRate());
                parc.setThumbnail(data.getThumbnail());
                parc.setReleaseDate(data.getReleaseDate());
                i.putExtra("parcel", parc);
                context.startActivity(i);
            }
        });

        holder.favT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLITE db = new SQLITE(context);
                if (holder.favT.isChecked()){
                    db.addToDB(mo);
                } else {
                    db.delete(mo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listtData.size();
    }

}
