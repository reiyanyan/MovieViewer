package com.reiyan.movie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.reiyan.movie.adapter.TabAdapter;
import com.reiyan.movie.fragment.NowPlayingFragment;
import com.reiyan.movie.fragment.UpComingFragment;

import java.util.Calendar;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle toggle;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    NotifHelpe helpe;
    private String URL = "https://api.themoviedb.org/3/search/movie?api_key=05d24d1094bc376832434c74ca08824f&language=en-US&query=%22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        components();

        sp = getSharedPreferences("users", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        adapter.addFragment(new NowPlayingFragment(), "Now Playing");
        adapter.addFragment(new UpComingFragment(), "Up Coming");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        startAlertAtParticularTime();

        /*helpe = new NotifHelpe(this);
        Notification.Builder builder = helpe.buildNotif(sp.getString("username", "null"),  sp.getString("password", "null"));
        helpe.getManager().notify(new Random().nextInt(), builder.build());*/
    }

    void components(){
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        adapter = new TabAdapter(getSupportFragmentManager());
    }

    public void startAlertAtParticularTime() {
        // alarm first vibrate at 14 hrs and 40 min and repeat itself at ONE_HOUR interval

        Intent intent = new Intent(this, BroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 35);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        helpe = new NotifHelpe(this);
        Notification.Builder builder = helpe.buildNotif(sp.getString("username", "null"),  sp.getString("password", "null"));
        helpe.getManager().notify(new Random().nextInt(), builder.build());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("URL", URL + query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        editor = sp.edit();

        switch (item.getItemId()){
            case R.id.favorite:
                startActivity(new Intent(this, FavActivity.class));
                break;
            case R.id.logout:
                editor.putBoolean("activity_signup", false);
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return false;
    }
}
