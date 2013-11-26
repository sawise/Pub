package com.group2.bottomapp;

import android.app.Activity;
import android.os.Bundle;


public class register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.register_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}