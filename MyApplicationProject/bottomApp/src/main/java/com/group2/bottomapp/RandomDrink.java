package com.group2.bottomapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
    private Cocktail cocktail;
    private int cocktailId;
    private int retries = 0;
    private int allowedRetries = 10;
    private boolean stopRetrying = false;

    private ProgressDialog progress;

    private ImageView ivDrinkImage;
    private TextView tvDrinkName;
    private Button btnNewRandom;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random, container, false);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");

        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);
        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        btnNewRandom = (Button) rootView.findViewById(R.id.btnNewRandom);


        cocktail = APIManager.getRandomDrink();
        if(cocktail != null){

            ivDrinkImage.setImageResource(cocktail.imageResourceId);

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
                    ivDrinkImage.setImageResource(newCocktail.imageResourceId);
                    tvDrinkName.setText(newCocktail.getName());
                    cocktailId = newCocktail.getId();
                }
            });


        } else {
            progress.show();

            startRefreshThread();
        }

        return rootView;
    }

    private void startRefreshThread(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                while (cocktail == null && !stopRetrying) {
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            cocktail = APIManager.getRandomDrink();

                            if (cocktail != null) {

                                ivDrinkImage.setImageResource(cocktail.imageResourceId);

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


                                progress.dismiss();
                            } else {
                                retries++;
                                if(retries < allowedRetries){
                                    stopRetrying = true;
                                    progress.dismiss();
                                }
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


    @Override
    public void onClick(View v) {

    }

}