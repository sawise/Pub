package com.group2.bottomapp;

/**
 * Created by FilipFransson on 2013-11-12.
 */  import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Drinks extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drinks, container, false);

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}
