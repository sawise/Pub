package com.group2.bottomapp;

    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Adapter;
    import android.widget.ArrayAdapter;
    import android.widget.GridView;

    import java.util.ArrayList;
    import java.util.List;


public class DrinksCabinet extends Fragment implements View.OnClickListener {

    private List<Integer> drinkList;
    private Integer[] drinkArray;
    private CustomListViewAdapter adapter;
    private GridView drinkGridView;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.drinkscapinet, container, false);

            drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);
            drinkList = new ArrayList<Integer>();
            for(int i = 0; i<=6; i++){
                drinkList.add(R.drawable.bottle_one);
                drinkList.add(R.drawable.bottle_two);
                drinkList.add(R.drawable.bottle_three);
            }

            //adapter = new CustomListViewAdapter(this.getActivity(), R.layout.drink_list, drinkList);
            drinkGridView.setAdapter(new ImageAdapter(this.getActivity(), drinkList));
            //drinkGridView.setAdapter(adapter);

            return rootView;
        }



        @Override
        public void onClick(View v) {

        }

}
