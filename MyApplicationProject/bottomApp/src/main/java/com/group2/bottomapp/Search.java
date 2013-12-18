package com.group2.bottomapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lukas on 2013-12-17.
 */
public class Search extends Activity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<Ingredient> searchAdapter;
    private Ingredient ingredientToEdit;



    List<Ingredient> mStrings = LiquorListAdapter.allIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.search_layout);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        searchAdapter = (new ArrayAdapter<Ingredient>(this,
                android.R.layout.simple_list_item_1,
                mStrings));
        mListView.setAdapter(searchAdapter);
        mListView.setTextFilterEnabled(true);
        registerForContextMenu(mListView);


        setupSearchView();
    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search for Ingredients");
    }


    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //get the country object
        ingredientToEdit = searchAdapter.getItem(aInfo.position);

        menu.setHeaderTitle(ingredientToEdit.getName());
        menu.add(1, 1, 1, "Add ingredient");

    }

    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // if item one is selected
        if(itemId == 1){
            APIManager.addIngredientToAccount(ingredientToEdit.getId());
            addToCabinet.handler.post(new Runnable() {
                @Override
                public void run() {
                    final ProgressDialog progDialog = ProgressDialog.show(MainActivity.getActivity(), "Loading",
                            "Please wait...", true);
                    new Thread() {
                        public void run() {
                            while(!(addToCabinet.loadStatus == "done" || addToCabinet.loadStatus == "fail")){
                                try {
                                    sleep(200);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            progDialog.dismiss();
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

        return true;
    }


}