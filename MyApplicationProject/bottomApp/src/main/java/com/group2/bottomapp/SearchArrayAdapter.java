package com.group2.bottomapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Svempa on 2013-12-18.
 */
public class SearchArrayAdapter extends ArrayAdapter<Ingredient> implements Filterable {

    private final Context context;
    private List<Ingredient> ingredientList;
    private List<Ingredient> origData;
    private Ingredient ingredient;
    private ImageView ingredientImage;
    private TextView ingredientNameText;
    private Button addIngredientButton;

    public SearchArrayAdapter(Context context, List<Ingredient> ingredientList){
        super(context, R.layout.search_list_layout, ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
        this.origData = ingredientList;
    }


    @Override
    public int getCount() {
        return (ingredientList == null) ? 0 : ingredientList.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // adds the ingredient to the cabinet
    private void addToCabinet(Ingredient ingredient){
        APIManager.addIngredientToAccount(ingredient.getId());

        final ProgressDialog progDialog = ProgressDialog.show(MainActivity.getActivity(), "Loading",
                "Please wait...", true, false);

        addToCabinet.handler.post(new Runnable() {
            @Override
            public void run() {

                new Thread() {
                    public void run() {
                        while(!(addToCabinet.loadStatus == "done" || addToCabinet.loadStatus == "fail")){
                            try {
                                sleep(200);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if(progDialog != null){
                            progDialog.dismiss();
                        }

                        Log.d("tjafs", addToCabinet.loadStatus);
                        if(addToCabinet.loadStatus == "done"){
                            addToCabinet.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.getAppContext(), "Ingredient was added to cabinet", 1000).show();
                                    addToCabinet.loadStatus = "";
                                }
                            });
                        } else if(addToCabinet.loadStatus == "fail"){
                            addToCabinet.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.getAppContext(), "Failed to add ingredient to cabinet\nMaybe you already have the ingredient?", 1000).show();
                                    addToCabinet.loadStatus = "";
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }



    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.search_list_layout, parent, false);

        ingredientNameText = (TextView) rowView.findViewById(R.id.ingredient_name);
        ingredientImage = (ImageView) rowView.findViewById(R.id.ingredient_image);
        addIngredientButton = (Button) rowView.findViewById(R.id.btnIngredientAdd);

        // get ingredient
        ingredient = ingredientList.get(position);

        // set ingredient text and image
        ingredientNameText.setText(ingredient.getName());
        ingredientImage.setImageResource(ingredient.imageResourceId);

        addIngredientButton.setTag(ingredient);

        // set onclick and handle onclick
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Ingredient selectedIngredient  = (Ingredient)v.getTag();
                addToCabinet(selectedIngredient);
            }
        });

        return rowView;
    }


    // Implements Filterable and the filtering is done below.
    // Need to override the object Ingredient .toString method as well

    @Override
    public Filter getFilter(){
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<Ingredient> founded = new ArrayList<Ingredient>();
                    for(Ingredient item: ingredientList){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                } else {
                    result.values = origData;
                    result.count = origData.size();
                }
                return result;


            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Now we have to inform the adapter about the new list filtered
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else {
                    ingredientList = (List<Ingredient>) results.values;
                    notifyDataSetChanged();
                }
            }

        };
    }

    // TODO handle server timeout??
}




