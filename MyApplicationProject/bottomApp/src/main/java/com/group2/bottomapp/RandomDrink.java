package com.group2.bottomapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class RandomDrink extends Fragment implements View.OnClickListener {
    private int cocktailId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random, container, false);

        final Cocktail cocktail = APIManager.getRandomDrink();
        if(cocktail == null){
            new AlertDialog.Builder(getActivity()).setMessage("Connect to internet!")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    }).create().show();
        } else {

            final ImageView ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);
            final TextView tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
            final Button btnNewRandom = (Button) rootView.findViewById(R.id.btnNewRandom);

            Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
            ivDrinkImage.setImageDrawable(image);

            tvDrinkName.setText(cocktail.getName());

            cocktailId = cocktail.getId();

            ivDrinkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new Drink(), cocktailId + "");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            btnNewRandom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cocktail newCocktail = APIManager.getRandomDrink();
                    while (newCocktail.equals(cocktail)) {
                        newCocktail = APIManager.getRandomDrink();
                    }
                    tvDrinkName.setText(newCocktail.getName());
                    cocktailId = newCocktail.getId();
                }
            });
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}