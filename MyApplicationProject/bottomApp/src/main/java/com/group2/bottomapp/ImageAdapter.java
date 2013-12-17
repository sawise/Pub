package com.group2.bottomapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * Created by sam on 12/17/13.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds;
    private List<Integer> itemsList;
    private LinkedHashMap<String, ArrayList<Ingredient>> items;


    public ImageAdapter(Context c, LinkedHashMap<String, ArrayList<Ingredient>> items){//;//List<Integer> items) {
        mContext = c;
        //this.itemsList = items;
        this.items = items;
        //mThumbIds = new Integer[itemsList.size()];
        //mThumbIds = itemsList.toArray(mThumbIds);
    }

    public int getCount() {
        return items.entrySet().size();//.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}


