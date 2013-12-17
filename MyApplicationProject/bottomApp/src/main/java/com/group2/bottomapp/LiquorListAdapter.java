package com.group2.bottomapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LiquorListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private HashMap<String, ArrayList<Ingredient>> ingredients;
    private ArrayList<Categories> categories;

    public LiquorListAdapter(Activity context) {
        this.context = context;

        categories = APIManager.getCategories();

        ingredients = new LinkedHashMap<String, ArrayList<Ingredient>>();

        for(Categories c : categories){
            ArrayList<Ingredient> tempList = APIManager.getIngredientsByCategory(c.getId());
            if(tempList.isEmpty()){
                Log.d("tjafsmannen", "listan är tom för fan");
            } else {
                Log.d("tjafsmannen", tempList.size() + " objekt finns i listjävlen");
            }
            ingredients.put(c.getName(), tempList);
        }

    }

    public Ingredient getChild(int groupPosition, int childPosition) {
        return ingredients.get(categories.get(groupPosition).getName()).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String ingredientName = getChild(groupPosition, childPosition).getName();
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
                Ingredient ing = ingredients.get(categories.get(groupPosition).getName()).get(childPosition);
                //CabinetManager.AddIngredient(getGroup(groupPosition) + ", " + ingredientName);
                APIManager.addIngredientToAccount(ing.getId());

            }
        });

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return ingredients.get(categories.get(groupPosition).getName()).size();
    }

    public Categories getGroup(int groupPosition) {
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
        String groupName = getGroup(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.liquorlistparent,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.textView);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(groupName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}