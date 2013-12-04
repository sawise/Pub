package com.group2.bottomapp;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Hugo on 2013-12-04.
 */
public class ShotRaceService extends Service {

    static Application application;
    static NotificationManager mNotificationManager = null;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals("START")) {
            showNotification();

        } else if(intent.getAction().equals("STOP")) {
            if(mNotificationManager != null){
                mNotificationManager.cancel(99);
            }
        }
        return 0;
    }

    public void showNotification(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Shot Race")
                        .setContentText("04:59");
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(application.getApplicationContext().NOTIFICATION_SERVICE);
        mNotificationManager.notify(99, mBuilder.build());

    }

    public static void setApplication(Application app){
        application = app;
    }
    @Override
    public IBinder onBind(Intent intent) {return null;}
}
