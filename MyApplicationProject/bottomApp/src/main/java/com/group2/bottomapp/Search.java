package com.group2.bottomapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

/**
 * Created by Lukas on 2013-12-17.
 */
public class Search extends Activity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<Ingredient> searchAdapter;

    List<Ingredient> mStrings = LiquorListAdapter.allIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.search_layout);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);

        searchAdapter = new SearchArrayAdapter(MainActivity.getActivity(), mStrings);

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
            searchAdapter.getFilter().filter("");
            mListView.clearTextFilter();
        } else {
            searchAdapter.getFilter().filter(newText.toString());
        }
        return true;
    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}