package com.group2.bottomapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 9/16/13.
 */
public class CustomListViewAdapter extends ArrayAdapter<Integer> {

    Context context;
    List<Integer> items;

    public CustomListViewAdapter(Context context, int resourceId, List<Integer> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView1;
        TextView textView;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Integer currentItem = items.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_layout, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView1 = (ImageView) convertView.findViewById(R.id.imgView1);
            //holder.drinksAdapter = new ArrayList<Integer>();


            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Log.i("List item", "" + currentItem);
        holder.imageView1.setImageResource(currentItem);


        return convertView;
    }
}