package com.group2.bottomapp;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
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

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class Drink extends Fragment implements View.OnClickListener {

    private String drinkName;
    private String drinkIngredients;
    private String drinkInstructions;
    private ImageView likeImage;
    private ImageView dislikeImage;

    private boolean favorite = false;


    private TextView tvDrinkName;
    private TextView tvDrinkIngredients;
    private TextView tvDrinkInstructions;
    private ImageView ivDrinkImage;

    private MenuItem favMenu;

    private int id;
    Cocktail cocktail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.drink, container, false);
        setHasOptionsMenu(true);

        id = 1;
        if(getTag() != null){
            id = Integer.parseInt(getTag());
        }

        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);

        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        tvDrinkIngredients = (TextView) rootView.findViewById(R.id.tvDrinkIngredients);
        tvDrinkInstructions = (TextView) rootView.findViewById(R.id.tvDrinkInstructions);

        likeImage = (ImageView) rootView.findViewById(R.id.like);
        dislikeImage = (ImageView) rootView.findViewById(R.id.dislike);

        //SharedPreferences prefs = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        likeImage.setOnClickListener(this);
        dislikeImage.setOnClickListener(this);

        initDrink(id);


        return rootView;
    }

    @Override
    public void onClick(View v) {

    if (v == likeImage){
        Connection connThread = new Connection(getActivity().getApplicationContext(), id);

        if(connThread.isConnected() != false){
            connThread.PutJsonUp();
            likeImage.setImageResource(R.drawable.thumbupblack);
            likeImage.setEnabled(false);
        }
        Log.i("Like", "");
    }
    else if (v == dislikeImage){
           Connection connThread = new Connection(getActivity().getApplicationContext(), id);

        if (connThread.isConnected() != false){
            connThread.PutJsonDown();
            dislikeImage.setImageResource(R.drawable.thumbdownblack);
            dislikeImage.setEnabled(false);
        }
        Log.i("Dislike", "");
    }

}


    public void initDrink(int id){

        cocktail = APIManager.getDrinkWithID(id);

        if(cocktail != null){
            int test = MainActivity.getAppContext().getResources().getIdentifier(cocktail.getName().replace(" ", "_").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName());
            if (test != 0) {
                //Personlig bild finns
                ivDrinkImage.setImageResource(MainActivity.getAppContext().getResources().getIdentifier(cocktail.getName().replace(" ", "_").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName()));
            } else {
                ivDrinkImage.setImageResource(R.drawable.ic_launcher);
            }

            tvDrinkName.setText(cocktail.getName());
            tvDrinkInstructions.setText(cocktail.getDescription());
            tvDrinkIngredients.setText(cocktail.getIngredientString("cl"));
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drinkmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        favMenu = (MenuItem) menu.findItem(R.id.starInMenu);

        ArrayList<Cocktail> drinksInFavorite = APIManager.getCocktailByFavorite(HelperClass.User.userId);
        for(Cocktail fav : drinksInFavorite){
            if(fav.getId() == id){
                favorite = true;
                favMenu.setIcon(R.drawable.menustaryellow);
                break;
            }
        }

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
            case R.id.starInMenu:
                if(!favorite){
                    favMenu.setIcon(getResources().getDrawable(R.drawable.menustaryellow));
                    APIManager.addFavoriteToAccount(id);
                    favorite = true;
                } else {
                    favorite = false;
                    favMenu.setIcon(getResources().getDrawable(R.drawable.menustarwhite));
                    APIManager.removeFavoriteToAccount(id);
                }
            default:
                break;
        }
        return false;
    }
}