package com.group2.bottomapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by sam on 11/26/13.
 */
public class SoundHelper {


    public static void start(int source, Context context){
        try{
            MediaPlayer mp = MediaPlayer.create(context, source);
            mp.start();
        } catch (Exception e){
            Log.i("MPerror", "" + e);
        }
    }
    public static void vibrate(Context context){
        Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(500);

    }
}
