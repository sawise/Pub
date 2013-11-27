package com.group2.bottomapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Drink extends Fragment implements View.OnClickListener {

    private String drinkName;
    private String drinkIngredients;
    private String drinkInstructions;
    private ImageView likeImage;
    private ImageView dislikeImage;

    private TextView tvDrinkName;
    private TextView tvDrinkIngredients;
    private TextView tvDrinkInstructions;
    private ImageView ivDrinkImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drink, container, false);

        int id = 1;
        if(getTag() != null){
            id = Integer.parseInt(getTag());
        }

        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);

        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        tvDrinkIngredients = (TextView) rootView.findViewById(R.id.tvDrinkIngredients);
        tvDrinkInstructions = (TextView) rootView.findViewById(R.id.tvDrinkInstructions);

        likeImage = (ImageView) rootView.findViewById(R.id.like);
        dislikeImage = (ImageView) rootView.findViewById(R.id.dislike);

        likeImage.setOnClickListener(this);
        dislikeImage.setOnClickListener(this);


        initDrink(id);

        return rootView;
    }


    @Override
    public void onClick(View v) {

    if (v == likeImage){
        Log.i("Like", "");
    }

    }


    public void initDrink(int id){

        Cocktail cocktail = APIManager.getDrinkWithID(id);

        Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
        ivDrinkImage.setImageDrawable(image);

        tvDrinkName.setText(cocktail.getName());
        tvDrinkInstructions.setText(cocktail.getDescription());
        tvDrinkIngredients.setText(cocktail.getIngredientString());
    }
}