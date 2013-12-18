package com.group2.bottomapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lukas on 2013-12-17.
 */
public class Search extends Activity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private ListView mListView;

    List<String> mStrings = LiquorListAdapter.allIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.search_layout);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mStrings));
        mListView.setTextFilterEnabled(true);
        Comparator<String> ALPHABETICAL_ORDER1 = new Comparator<String>()
        {
            public int compare(String object1, String object2)
            {
                int res = String.CASE_INSENSITIVE_ORDER.compare(object1.toString(), object2.toString());
                return res;
            }
        };

        Collections.sort(mStrings, ALPHABETICAL_ORDER1);
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
}