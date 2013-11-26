package com.group2.bottomapp;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hugo on 2013-11-20.
 */
public class AvailableDrinkListAdapter extends BaseAdapter {
    ArrayList<Cocktail> cocktails;
    Context context;
    View.OnClickListener clicklistener;


    public AvailableDrinkListAdapter(Context context, View.OnClickListener clicklistener){
        this.context = context;
        this.cocktails = (ArrayList) APIManager.getAllAvailableCocktails();
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

        Drawable image = context.getResources().getDrawable(R.drawable.ic_launcher);
        ivDrinkImage.setImageDrawable(image);

        tvDrinkName.setText(cocktails.get(i).getName());

        view.setTag(cocktails.get(i).getId());
        view.setOnClickListener(clicklistener);

        return view;
    }
}
