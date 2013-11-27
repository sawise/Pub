package com.group2.bottomapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Hugo on 2013-11-27.
 */
public class ShotRace extends Fragment implements View.OnClickListener {

    private Button btnStart;
    private TextView tvClock;
    private boolean isActive = false;
    private int minutesToAlarmTrigger = 5;

    private Calendar startCal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shotrace, container, false);

        tvClock = (TextView) rootView.findViewById(R.id.textView);
        tvClock.setText(preZero(minutesToAlarmTrigger) + ":00");

        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isActive){
                    startRace();
                } else {
                    stopRace();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {

    }

    private void startTimerThread() {
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
                                Calendar newCal = Calendar.getInstance();
                                int minutePassed = newCal.get(Calendar.MINUTE) - startCal.get(Calendar.MINUTE);
                                int secondsPassed = newCal.get(Calendar.SECOND) - startCal.get(Calendar.SECOND);

                                int minuteLeft = (minutesToAlarmTrigger - 1) - minutePassed;
                                int secondsLeft = 59 - secondsPassed;

                                if(secondsLeft > 59){
                                    secondsLeft -= 60;
                                    minuteLeft += 1;
                                }
                                tvClock.setText(preZero(minuteLeft) + ":" + preZero(secondsLeft));

                                if(minuteLeft == 0 && secondsLeft == 0){
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

    private void triggerAlarm() {

        tvClock.setText("00:00");
        isActive = false;
        SoundHelper.vibrate(getActivity().getApplicationContext());
        SoundHelper.start(R.raw.startup, this.getActivity());

        new AlertDialog.Builder(getActivity())
                .setTitle("Take a shot!")
                .setMessage("Even though you won't believe me " + minutesToAlarmTrigger + " minutes has passed, and you know what that means.")
                .setPositiveButton("Another one!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restartRace();
                    }
                })
                .setNegativeButton("Ahw hell no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        stopRace();
                    }
                })
                .show();
    }

    private void startRace(){
        isActive = true;
        btnStart.setText("I can't fucking handle more bro'");

        startCal = Calendar.getInstance();
        tvClock.setText(preZero(minutesToAlarmTrigger - 1) + ":59");
        startTimerThread();
    }

    private void restartRace() {
        startRace();
    }

    private void stopRace(){
        isActive = false;
        btnStart.setText("I'm ready! Let's do it");

        tvClock.setText(preZero(minutesToAlarmTrigger) + ":00");
    }

    private String preZero(int i){
        if(i < 10){
            return "0" + i;
        }
        return i + "";
    }
}
