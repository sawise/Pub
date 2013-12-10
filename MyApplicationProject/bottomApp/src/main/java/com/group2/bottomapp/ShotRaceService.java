package com.group2.bottomapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Hugo on 2013-12-04.
 */
public class ShotRaceService extends Service {

    public static boolean isActive;
    public static int secondsLeft;
    static NotificationManager mNotificationManager = null;
    private static Activity activity;

    public static void setActivity(Activity activity){
        ShotRaceService.activity = activity;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals("STOP")) {
            isActive = false;
            if(mNotificationManager != null){
                mNotificationManager.cancel(99);
            }
        } else {
            String actionString = intent.getAction();
            final int startMinutes = Integer.parseInt(actionString.split(":")[0]);
            final int startSeconds = Integer.parseInt(actionString.split(":")[1]);
            secondsLeft = startMinutes * 60 + startSeconds;
            showNotification();

            isActive = true;

            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {

                public void run() {
                    do {
                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable(){
                            public void run() {
                                if(isActive){

                                    secondsLeft--;

                                    showNotification();

                                    if(secondsLeft == 0){
                                        triggerAlarm();
                                    }

                                }
                            }
                        });
                    } while (isActive);
                }
            };
            new Thread(runnable).start();
        }
        return 0;
    }

    public void showNotification(){
        int minutes = (int)Math.floor(secondsLeft/60);
        int seconds = (int)secondsLeft%60;
        String contentText = preZero(minutes) + ":" + preZero(seconds);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Shot Race")
                        .setContentText(contentText);
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setAction("ShotRace");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(99, mBuilder.build());

    }

    private void triggerAlarm() {

        isActive = false;
        SoundHelper.vibrate(activity.getApplicationContext());
        SoundHelper.start(R.raw.hornair, activity);

<<<<<<< HEAD
        new AlertDialog.Builder(activity)
                .setTitle("Take a shot!")
                .setMessage("It's already time for another shot!")
                .setPositiveButton("Another one!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //restartRace();
                    }
                })
                .setNegativeButton("Ahw hell no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //stopRace();
                    }
                })
                .show();
=======
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("ShotRace");

        startActivity(intent);
>>>>>>> fc0993bdecde5406753a98c83cc7257e1d00483c
    }

    @Override
    public IBinder onBind(Intent intent) {return null;}

    private String preZero(int i){
        if(i < 10){
            return "0" + i;
        }
        return i + "";
    }
}
