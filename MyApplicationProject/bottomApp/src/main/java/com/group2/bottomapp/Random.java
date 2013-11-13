package com.group2.bottomapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Random extends Fragment implements View.OnClickListener {
    private ImageView ivDrinkImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random, container, false);


        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);


        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}