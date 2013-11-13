package com.group2.bottomapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Adapter;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.GridView;

    import java.util.ArrayList;
    import java.util.List;


public class DrinksCabinet extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {

    private List<Integer> drinkList;
    private GridView drinkGridView;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.drinkscapinet, container, false);
            setHasOptionsMenu(true);

            drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);
            drinkList = new ArrayList<Integer>();
            for(int i = 0; i<=6; i++){
                drinkList.add(R.drawable.bottle_one);
                drinkList.add(R.drawable.bottle_two);
                drinkList.add(R.drawable.bottle_three);
            }

            drinkGridView.setAdapter(new ImageAdapter(this.getActivity(), drinkList));

            drinkGridView.setOnItemClickListener(this);


            return rootView;
        }



        @Override
        public void onClick(View v) {

        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == drinkGridView){
            Log.i("gridview", "item:"+position);
        }
    }
    /*    @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // TODO Add your menu entries here
            Log.i("menu", "yees");
            inflater.inflate(R.menu.drinkscabinetmenu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToCabinet:

                Intent intent = new Intent(this.getActivity(), addToCabinet.class);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return false;
    }*/
}
