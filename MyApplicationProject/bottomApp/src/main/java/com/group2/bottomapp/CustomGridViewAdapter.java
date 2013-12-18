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
            holder.txtCategory = (TextView) row.findViewById(R.id.rowCatname);
            holder.drinkID = (TextView) row.findViewById(R.id.rowDrinkID);
            row.setTag(holder);

        } else {
            holder = (RecordHolder) row.getTag();
        }
        Ingredient item = data.get(position);
        String catName;
        if(item.getCategoryName().contains("/")){
            String[] catSplit = item.getCategoryName().split("/");
            String split1 = catSplit[0];
            String split2 = catSplit[1];
            catName = split1+"\r\n"+split2;
        } else{
            catName = item.getCategoryName();
        }

        String id = Integer.toString(item.getId());

        holder.drinkID.setText(id);
        holder.txtCategory.setText(catName);//Character.toString(catName));
        holder.txtTitle.setText(item.getName());

        int test = MainActivity.getAppContext().getResources().getIdentifier(item.getName().replace(" ", "_").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName());
        if (test != 0) {
            //Personlig bild finns
            holder.imageItem.setImageResource(MainActivity.getAppContext().getResources().getIdentifier(item.getName().replace(" ", "_").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName()));
        } else {
            holder.imageItem.setImageResource(R.drawable.bottle_threee);
        }
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        TextView txtCategory;
        TextView drinkID;
    }

}
