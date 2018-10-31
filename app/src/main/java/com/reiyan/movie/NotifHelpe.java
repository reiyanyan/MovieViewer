package com.reiyan.movie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.util.Calendar;

public class NotifHelpe extends ContextWrapper {

    public static final String id = "com.reiyan";
    public static final String name = "Now Playing";
    NotificationManager manager;

    public NotifHelpe(Context base) {
        super(base);
        CreateChannel();
    }

    public void CreateChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);

            getManager().createNotificationChannel(channel);
        }
    }

    public Notification.Builder buildNotif (String title, String body){

        Intent intent = new Intent(this, FavActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getApplicationContext(), id);
        }
        builder.setContentText(body);
        builder.setContentTitle(title);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);


        return  builder;
    }

    public NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
