package com.group2.bottomapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Favorites extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {

    private ListView favvoList;

    private int likeUp;
    private int likeDown;
    private ListView drinkList;
    private int selectedItem;
    private TextView cabinet;
    private int retries = 0;
    private int allowedRetries = 10;
    private boolean stopRetrying = false;

    private ProgressDialog progress;
    ArrayList<Cocktail> drinksInFavorite;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.favorite_list, container, false);
    setHasOptionsMenu(true);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.show();

        cabinet = (TextView)rootView.findViewById(R.id.txtFavorite);
    drinkList = (ListView)rootView.findViewById(R.id.favoriteDrinkListInside);

    cabinet.setTextColor(getResources().getColor(R.color.ColorWhite));

    if(HelperClass.User.userName.endsWith("s")){
        cabinet.setText(HelperClass.User.userName + " " + "Favorites");
    }else{
        cabinet.setText(HelperClass.User.userName + "'s Favorites");
    }

    drinksInFavorite = APIManager.getCocktailByFavorite(HelperClass.User.userId);

        if(!drinksInFavorite.isEmpty()) {
            AvailableFavoriteDrinkListAdapter availableDrinkListAdapter = new AvailableFavoriteDrinkListAdapter(getActivity().getApplicationContext(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new Drink(), view.getTag() + "");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }, HelperClass.User.userId);
            drinkList.setAdapter(availableDrinkListAdapter);
            progress.dismiss();
        } else {
            startRefreshThread();
        }
        return rootView;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                            drinksInFavorite = APIManager.getCocktailByFavorite(HelperClass.User.userId);
                            if (!drinksInFavorite.isEmpty()) {
                                Log.i("threadd"+HelperClass.User.userId,"try " + retries + ":not empty");
                                AvailableFavoriteDrinkListAdapter availableDrinkListAdapter = new AvailableFavoriteDrinkListAdapter(getActivity().getApplicationContext(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.main, new Drink(), view.getTag() + "");
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                }, HelperClass.User.userId);
                                drinkList.setAdapter(availableDrinkListAdapter);
                                progress.dismiss();
                            } else {
                                retries++; Log.i("threadd","try " + retries + ": empty");
                                if(retries < allowedRetries){
                                    stopRetrying = true;
                                    progress.dismiss();
                                }
                            }
                        }
                    });
                } while (drinksInFavorite.isEmpty() && !stopRetrying);
            }
        };
        new Thread(runnable).start();
    }
}