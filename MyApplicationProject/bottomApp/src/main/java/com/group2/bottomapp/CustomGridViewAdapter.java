package com.group2.bottomapp;

/**
 * Created by sam on 12/17/13.
 */import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewAdapter extends ArrayAdapter<Ingredient> {
    Context context;

    int layoutResourceId;

    ArrayList<Ingredient> data = new ArrayList<Ingredient>();



    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Ingredient> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            holder.drinkID = (TextView) row.findViewById(R.id.rowDrinkID);
            row.setTag(holder);

        } else {
            holder = (RecordHolder) row.getTag();
        }
        Ingredient item = data.get(position);


        String id = Integer.toString(item.getId());

        holder.drinkID.setText(id);
        holder.txtTitle.setText(item.getName());

        holder.imageItem.setImageResource(item.imageResourceId);

        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        TextView drinkID;
    }

}
