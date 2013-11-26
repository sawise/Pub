package com.group2.bottomapp;

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

        List<Cocktail> cocktails = APIManager.getAllAvailableCocktails();

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}
