package com.group2.bottomapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Hugo on 2013-11-13.
 */
public class LiquorListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, ArrayList<String>> ingredients;
    private ArrayList<String> categories;

    public LiquorListAdapter(Activity context) {
        this.context = context;

        categories = new ArrayList<String>();
        categories.add("Vodka");
        categories.add("Other");


        ingredients = new LinkedHashMap<String, ArrayList<String>>();

        ArrayList<String> children = new ArrayList<String>();
        children.add("Blueberry");
        children.add("Raspberry");
        children.add("Pear");

        ingredients.put("Vodka", children);

        children = new ArrayList<String>();
        children.add("Lime");
        children.add("Lemon");
        children.add("Ice");

        ingredients.put("Other", children);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ingredients.get(categories.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String ingredientName = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.liquorlistchild, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.textView);
        item.setText(ingredientName);

        Button btnAdd = (Button) convertView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CabinetManager.AddIngredient(getGroup(groupPosition) + ", " + ingredientName);
            }
        });

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return ingredients.get(categories.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    public int getGroupCount() {
        return categories.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.liquorlistparent,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.textView);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}