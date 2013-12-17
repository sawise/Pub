package com.group2.bottomapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.group2.bottomapp.utils.SectionableAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A sample Adapter that demonstrates one use of the SectionableAdapter class,
 * using a hard-coded two-dimensional array for the data source.
 * 
 * @author Velos Mobile
 */
/*
 * Copyright � 2012 Velos Mobile
 */
public class BookcaseAdapter extends SectionableAdapter implements
		View.OnClickListener {

	// For simplicity, we hard-code the headers and data. In an actual app, this
	// can come from the network, the filesystem, SQLite, or any of the 
	// usual suspects.
    String[] Ings;
    String[] IngsID;
    String[] Cats;
    String[] CatID;
	static final String[] AUTHORS = new String[] { "Roberto Bola�o",
			"David Mitchell", "Haruki Murakami", "Thomas Pynchon" };
	private static final String[][] BOOKS = new String[][] {
			{ "The Savage Detectives", "2666" },
			{ "Ghostwritten", "number9dream", "Cloud Atlas",
					"Black Swan Green", "The Thousand Autumns of Jacob de Zoet" },
			{ "A Wild Sheep Chase",
					"Hard-Boiled Wonderland and the End of the World",
					"Norwegian Wood", "Dance Dance Dance",
					"South of the Border, West of the Sun",
					"The Wind-Up Bird Chronicle", "Sputnik Sweetheart",
					"Kafka on the Shore", "After Dark", "1Q84" },
			{ "V.", "The Crying of Lot 49", "Gravity's Rainbow", "Vineland",
					"Mason & Dixon", "Against the Day", "Inherent Vice" } };

	private Activity activity;
    private LinkedHashMap<String, ArrayList<Ingredient>> items;
    private ArrayList<Categories> categories;
    private int sectionSize;


    public BookcaseAdapter(Activity activity, LayoutInflater inflater,
			int rowLayoutID, int headerID, int itemHolderID, int resizeMode, String[] Ings, String[] Cats) {
		super(inflater, rowLayoutID, headerID, itemHolderID, resizeMode, Ings, Cats);
		this.activity = activity;
        //this.sectionSize = Cats.length;
        this.Ings = Ings;
        this.Cats = Cats;

        this.items = items;
        this.categories = categories;
        this.sectionSize = categories.size();
        //Log.i("bookcasee",categories.entrySet().size()+"");
	}


    public void getItems(){
        Iterator<Map.Entry<String, ArrayList<Ingredient>>> itr = items.entrySet().iterator();

        while(itr.hasNext()){
            Map.Entry<String,  ArrayList<Ingredient>> itrr = itr.next();
            Log.i("itrrr",itrr.getKey()+"<->"+itrr.getValue());
        }
    }



    @Override
    protected void bindView(View convertView, int position) {
        String title = (String) getItem(position);
        String id = "1";
        TextView label = (TextView) convertView.findViewById(R.id.bookItem_title);
        TextView labelId = (TextView) convertView.findViewById(R.id.drinkID);

        label.setText(title);
        labelId.setText(id);
        convertView.setOnClickListener(this);
    }

    @Override
    protected String getHeaderForSection(int section) {
        /*Iterator<Map.Entry<String, ArrayList<Ingredient>>> itr = items.entrySet().iterator();
        String value = "";
        while(itr.hasNext()){
            Map.Entry<String,  ArrayList<Ingredient>> itrr = itr.next();
            Log.i("itrrr",itrr.getKey()+"<->"+itrr.getValue());
            value += itrr.getKey()+",";
        }
        Cats = value.split(",");*/
        Log.i("secc", section+"");
        return categories.get(1).getName();
        //return AUTHORS[section];
    }


    @Override
	public Object getItem(int position) {

        Log.i("poss", ""+position);
        //Log.i("size", ""+items.entrySet().size());
      //  String value = "";
        //String id = "";
       /* Iterator<Map.Entry<String, ArrayList<Ingredient>>> itr = items.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String,  ArrayList<Ingredient>> itrr = itr.next();
            for(Ingredient ing : itrr.getValue()){
                value += ing.getName()+",";
                id += ing.getId()+",";
            }
        }*/
        //Ings = value.split(",");
        //IngsID = id.split(",");
		//for (int i = 0; i < Ings.length; ++i) {
		//	if (position < Ings.length) {
        ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByCategory(categories.get(position).getId());
                Log.i("poss getItem", "<->"+position);
        return ingredientsinCat.get(position).getName();
				//return Ings[position];
		//	}
		//	position -= Ings.length;
		//}
		// This will never happen.
		//return null;
	}

	@Override
	protected int getDataCount() {
		int total = 0;
		for (int i = 0; i < BOOKS.length; ++i) {
			total += BOOKS[i].length;
		}
		return 3;
	}

	@Override
	protected int getSectionsCount() {
        //categories = APIManager.getCategories();
        Log.i("cattt", "" + Cats.length);
		return Cats.length;
	}



	@Override
	protected int getCountInSection(int index) {
        //ArrayList<Ingredient> ings = APIManager.getIngredientsByCategory(2);
        return 1;
	}

	@Override
	protected int getTypeFor(int position) {
		int runningTotal = 0;
		int i = 0;
		for (i = 0; i < BOOKS.length; ++i) {
			int sectionCount = BOOKS[i].length;
			if (position < runningTotal + sectionCount)
				return i;
			runningTotal += sectionCount;
            Log.i("poss gettypefor", i+"<->"+runningTotal);
		}
		// This will never happen.
		return -1;
	}




	@Override
	public void onClick(View v) {
		//Intent i = new Intent(Intent.ACTION_SEARCH);
		TextView label = (TextView) v.findViewById(R.id.bookItem_title);
        TextView id = (TextView) v.findViewById(R.id.drinkID);
		String text = label.getText().toString();
        String idText = id.getText().toString();
        Log.i("iddd", idText.toString());
		//i.putExtra(SearchManager.QUERY, text);
		//activity.startActivity(i);
	}

}
