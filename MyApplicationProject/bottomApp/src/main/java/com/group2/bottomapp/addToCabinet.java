package com.group2.bottomapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


public class addToCabinet extends Fragment {

    private ExpandableListView liquorList;

    static public Handler handler;
    static public String loadStatus = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addtocabinet, container, false);
        setHasOptionsMenu(true);

        handler = new Handler();

        liquorList = (ExpandableListView) rootView.findViewById(R.id.liquorList);
        final LiquorListAdapter liquorListAdapter = new LiquorListAdapter(getActivity());

        liquorList.setAdapter(liquorListAdapter);

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addtocabinetsearch, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent i = new Intent(getActivity().getApplicationContext(), Search.class);
                startActivity(i);
                return true;
            default:
                break;
        }
        return false;
    }

    private void openSettings() {

    }

    private void openSearch() {

    }

}
