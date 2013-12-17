package com.group2.bottomapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;



public class Drink extends Fragment implements View.OnClickListener {

    private String drinkName;
    private String drinkIngredients;
    private String drinkInstructions;
    private ImageView likeImage;
    private ImageView dislikeImage;
    private ImageView starwhite;

    private TextView tvDrinkName;
    private TextView tvDrinkIngredients;
    private TextView tvDrinkInstructions;
    private ImageView ivDrinkImage;

    private int id;
    Cocktail cocktail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drink, container, false);

        id = 1;
        if(getTag() != null){
            id = Integer.parseInt(getTag());
        }
        cocktail = APIManager.getDrinkWithID(id);

        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);



        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        tvDrinkIngredients = (TextView) rootView.findViewById(R.id.tvDrinkIngredients);
        tvDrinkInstructions = (TextView) rootView.findViewById(R.id.tvDrinkInstructions);

        likeImage = (ImageView) rootView.findViewById(R.id.like);
        dislikeImage = (ImageView) rootView.findViewById(R.id.dislike);
        starwhite = (ImageView) rootView.findViewById(R.id.starwhite);


        likeImage.setOnClickListener(this);
        dislikeImage.setOnClickListener(this);
        starwhite.setOnClickListener(this);

        initDrink(id);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onClick(View v) {

    if (v == likeImage){
        Connection connThread = new Connection(getActivity().getApplicationContext(), id);
        ImageView imageView = (ImageView) v;
        imageView.setVisibility(View.INVISIBLE);

        if(connThread.isConnected() != false){
            connThread.PutJsonUp();
        }
        Log.i("Like", "");
    }
    else if (v == dislikeImage){
           Connection connThread = new Connection(getActivity().getApplicationContext(), id);
        ImageView imageView = (ImageView) v;
        imageView.setVisibility(View.INVISIBLE);

        if (connThread.isConnected() != false){
            connThread.PutJsonDown();
        }
        Log.i("Dislike", "");
    }

    else if (v == starwhite) {
        starwhite.setImageDrawable(getResources().getDrawable(R.drawable.staryellow));

    }
}


    public void initDrink(int id){

        cocktail = APIManager.getDrinkWithID(id);

        if(cocktail != null){
            Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
            ivDrinkImage.setImageDrawable(image);

            tvDrinkName.setText(cocktail.getName());
            tvDrinkInstructions.setText(cocktail.getDescription());
            tvDrinkIngredients.setText(cocktail.getIngredientString("cl"));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drinkmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cl:
                tvDrinkIngredients.setText(cocktail.getIngredientString("cl"));
                return true;
            case R.id.oz:
                tvDrinkIngredients.setText(cocktail.getIngredientString("oz"));
                return true;
            default:
                break;
        }
        return false;
    }
}