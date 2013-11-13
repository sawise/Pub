package com.group2.bottomapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


public class addToCabinet extends Fragment implements View.OnClickListener {

    private ExpandableListView liquorList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addtocabinet, container, false);

        liquorList = (ExpandableListView) rootView.findViewById(R.id.liquorList);
        final LiquorListAdapter liquorListAdapter = new LiquorListAdapter(getActivity());

        liquorList.setAdapter(liquorListAdapter);

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}
