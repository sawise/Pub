package com.group2.bottomapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.group2.bottomapp.utils.SectionableAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A sample application that demonstrates use of the SectionableAdapter to
 * show a scrolling, sectioned grid representing books on a bookshelf.
 * @author Velos Mobile.
 */
/*
 * Copyright � 2012 Velos Mobile
 */
public class SectionedGridActivity extends Fragment implements ListView.OnItemClickListener {

    Datasett dataSett;
    String[] Ings;
    String[] Cats;
    String[] IngsID;
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sectioned_grid, container, false);
        //View rootView = inflater.inflate(R.layout.linearlayout1, container, false);
        setHasOptionsMenu(true);

        dataSett = new Datasett();

        /*progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.show();*/


        ArrayList<Categories> categories = APIManager.getCategories();

        String catStr = "";
        //LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayout1);
        for(Categories cat : categories){
            //add LayoutParams



            Log.i("Catt", cat.getName() + " " + cat.getId());
            ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByCategory(cat.getId());
            Log.i("Catt", ingredientsinCat.size() + "");
            catStr += cat.getName()+",";
            dataSett.addSection(cat.getName(), ingredientsinCat);
        }
        //Log.i("getSec", dataSett.getSections());
        LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter = dataSett.getSections();
        int hashMapSize = dataSett.getSections().entrySet().size();
        Log.i("getSec", hashMapSize+" sizee");
		ListView list = (ListView) rootView.findViewById(R.id.sectionedGrid_list);

        String value = "";
        String id = "";
        Iterator<Map.Entry<String, ArrayList<Ingredient>>> itr = dataSett.getSections().entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String,  ArrayList<Ingredient>> itrr = itr.next();
            for(Ingredient ing : itrr.getValue()){
                value += ing.getName()+",";
                id += ing.getId()+",";
            }
        }
        Cats = value.split(",");
        Ings = value.split(",");
        IngsID = id.split(",");

        BookcaseAdapter adapter = new BookcaseAdapter(this.getActivity(), inflater, R.layout.book_row, R.id.bookRow_header,
				R.id.bookRow_itemHolder, SectionableAdapter.MODE_VARY_WIDTHS, Ings, Cats);
                //BookcaseAdapter adapter = new BookcaseAdapter(this.getActivity(),
                   //     inflater, R.layout.book_row_vary_columns, R.id.bookRow_header,
                 //       R.id.bookRow_itemHolder, SectionableAdapter.MODE_VARY_COUNT, Ings, Cats);
		list.setAdapter(adapter);
		list.setDividerHeight(0);


        list.setOnItemClickListener(this);

        return rootView;
	}




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("secgrid", i+"<->"+l);
    }
}
