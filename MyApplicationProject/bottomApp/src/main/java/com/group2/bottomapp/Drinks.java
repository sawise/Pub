package com.group2.bottomapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Drinks extends Fragment implements View.OnClickListener {

    private ListView drinkList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drinks, container, false);

        List<Cocktail> cocktails = APIManager.getAllAvailableCocktails();

        if(cocktails == null){
            new AlertDialog.Builder(getActivity()).setMessage("Connect to internet!")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    }).create().show();
        } else {

            drinkList = (ListView) rootView.findViewById(R.id.drinkList);
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
        }

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}
