package com.group2.bottomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hugo on 2013-11-20.
 */
public class AvailableFavoriteDrinkListAdapter extends BaseAdapter {
    ArrayList<Cocktail> cocktails;
    Context context;
    View.OnClickListener clicklistener;


    public AvailableFavoriteDrinkListAdapter(Context context, View.OnClickListener clicklistener, int userID){
        this.context = context;
        this.cocktails = (ArrayList) APIManager.getCocktailByFavorite(userID);
        this.clicklistener = clicklistener;
    }

    @Override
    public int getCount() {
        return cocktails.size();
    }

    @Override
    public Object getItem(int i) {
        return cocktails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.cocktail_listobject, null);
        }

        ImageView ivDrinkImage = (ImageView) view.findViewById(R.id.ivDrinkImage);
        TextView tvDrinkName = (TextView) view.findViewById(R.id.tvDrinkName);

        ivDrinkImage.setImageResource(cocktails.get(i).imageResourceId);

        tvDrinkName.setText(cocktails.get(i).getName());
        tvDrinkName.setTextColor(context.getResources().getColor(R.color.ColorBlack));

        view.setTag(cocktails.get(i).getId());
        view.setOnClickListener(clicklistener);

        return view;
    }
}
