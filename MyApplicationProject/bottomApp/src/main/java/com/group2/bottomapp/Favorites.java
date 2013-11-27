package com.group2.bottomapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Favorites extends Fragment implements View.OnClickListener {

    private ListView favvoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorites, container, false);
        favvoList = (ListView)rootView.findViewById(R.id.favList);


        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}