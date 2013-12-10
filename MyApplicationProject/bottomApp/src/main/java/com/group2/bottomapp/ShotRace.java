package com.group2.bottomapp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * Created by Hugo on 2013-11-27.
 */
public class ShotRace extends Fragment implements View.OnClickListener {

    private Button btnStart;
    private TextView tvClock;
    private boolean isActive = false;
    private int minutesToAlarmTrigger = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shotrace, container, false);

        ShotRaceService.setActivity(getActivity());

        tvClock = (TextView) rootView.findViewById(R.id.textView);
        tvClock.setText(preZero(minutesToAlarmTrigger) + ":00");

        tvClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isActive){
                    final Dialog d = new Dialog(getActivity());
                    d.setTitle("Set minutes between shots.");
                    d.setContentView(R.layout.shotracetimesliderdialog);

                    final TextView textView = (TextView) d.findViewById(R.id.textView);
                    Button b1 = (Button) d.findViewById(R.id.button1);
                    Button b2 = (Button) d.findViewById(R.id.button2);
                    final SeekBar sb = (SeekBar) d.findViewById(R.id.seekBar);
                    sb.setProgress(minutesToAlarmTrigger - 1);
                    textView.setText(minutesToAlarmTrigger + " min");

                    sb.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
                    {
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser)
                        {
                            textView.setText((progress + 1) + " min");
                        }
                        public void onStartTrackingTouch(SeekBar seekBar){}
                        public void onStopTrackingTouch(SeekBar seekBar){}
                    });

                    //np.setOnValueChangedListener(this);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            minutesToAlarmTrigger = sb.getProgress() + 1;
                            tvClock.setText(preZero(sb.getProgress() + 1) + ":00");
                            d.dismiss();
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                    d.show();
                }
            }
        });

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


        if(isShotRaceServiceRunning()){

            int minutes = (int)Math.floor(ShotRaceService.secondsLeft/60);
            int seconds = ShotRaceService.secondsLeft%60;
            String contentText = preZero(minutes) + ":" + preZero(seconds);
            tvClock.setText(contentText);
            resumeRace(minutes, seconds);

            Intent intent = new Intent(getActivity().getApplicationContext(), ShotRaceService.class);
            intent.setAction("STOP");
            getActivity().startService(intent);
            getActivity().stopService(intent);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPause(){
        super.onPause();

        if(isActive){
            Intent intent = new Intent(getActivity().getApplicationContext(), ShotRaceService.class);
            intent.setAction(tvClock.getText() + "");
            getActivity().startService(intent);
        }
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

                                int minutes = Integer.parseInt(tvClock.getText().toString().split(":")[0]);
                                int seconds = Integer.parseInt(tvClock.getText().toString().split(":")[1]);

                                seconds--;
                                if(seconds == 0){
                                    minutes--;
                                }

                                tvClock.setText(preZero(minutes) + ":" + preZero(seconds));

                                if(seconds == 0 && minutes == 0){
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
        SoundHelper.start(R.raw.hornair, this.getActivity());

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
        tvClock.setText(preZero(minutesToAlarmTrigger - 1) + ":59");
        startTimerThread();
    }

    private void resumeRace(int minutes, int seconds){
        isActive = true;
        btnStart.setText("I can't fucking handle more bro'");
        startTimerThread();
    }

    private void restartRace() {
        startRace();
    }

    private void stopRace(){
        isActive = false;
        btnStart.setText("I'm ready! Let's do it");
        SoundHelper.start(R.raw.minionlaugh, this.getActivity());

        tvClock.setText(preZero(minutesToAlarmTrigger) + ":00");
    }

    private boolean isShotRaceServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(getActivity().getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (ShotRaceService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private String preZero(int i){
        if(i < 10){
            return "0" + i;
        }
        return i + "";
    }
}
