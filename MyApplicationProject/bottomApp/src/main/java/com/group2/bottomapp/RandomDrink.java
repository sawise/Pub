package com.group2.bottomapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RandomDrink extends Fragment implements View.OnClickListener {
    private ImageView ivDrinkImage;
    private TextView tvDrinkName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random, container, false);

        Cocktail cocktail = APIManager.getRandomDrink();

        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);
        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);

        Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
        ivDrinkImage.setImageDrawable(image);

        String name = cocktail.getName();
        tvDrinkName.setText(name);

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}