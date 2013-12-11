package com.group2.bottomapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Drinks extends Fragment {

    private ListView drinkList;
    private List<Cocktail> cocktails;
    private int retries = 0;
    private int allowedRetries = 10;
    private boolean stopRetrying = false;

    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drinks, container, false);
        drinkList = (ListView) rootView.findViewById(R.id.drinkList);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.show();

        cocktails = APIManager.getAllAvailableCocktails();

        if(!cocktails.isEmpty()) {
            AvailableDrinkListAdapter availableDrinkListAdapter = new AvailableDrinkListAdapter(getActivity().getApplicationContext(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new Drink(), view.getTag() + "");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
            drinkList.setAdapter(availableDrinkListAdapter);
            progress.dismiss();
        } else {
            startRefreshThread();
        }

        return rootView;
    }

    private void startRefreshThread(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                do {
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            cocktails = APIManager.getAllAvailableCocktails();

                            if (!cocktails.isEmpty()) {

                                AvailableDrinkListAdapter availableDrinkListAdapter = new AvailableDrinkListAdapter(getActivity().getApplicationContext(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.main, new Drink(), view.getTag() + "");
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                });
                                drinkList.setAdapter(availableDrinkListAdapter);
                                progress.dismiss();
                            } else {
                                retries++;
                                if(retries < allowedRetries){
                                    stopRetrying = true;
                                }
                            }
                        }
                    });
                } while (cocktails.isEmpty() && !stopRetrying);
            }
        };
        new Thread(runnable).start();
    }
}
