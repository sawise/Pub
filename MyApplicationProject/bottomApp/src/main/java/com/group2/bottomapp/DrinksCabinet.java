package com.group2.bottomapp;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.group2.bottomapp.SectionedGridViewAdapter.OnGridItemClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class DrinksCabinet extends Fragment implements View.OnClickListener, GridView.OnItemClickListener, OnGridItemClickListener {


    //public ArrayList<HashMap<Integer, String>> drinkListt = new ArrayList<HashMap<Integer, String>>();
    protected static final String TAG = "DrinksCabinet";
    private ListView listView;
    private Dataset dataSet;
    private SectionedGridViewAdapter adapter = null;
    private LinkedHashMap<String, Cursor> cursorMap;

    private  ArrayList<Cocktail> gridArray = new ArrayList<Cocktail>();

    private CabinetManager cabinetManager;
    private int selectedItem;
    private String[] testStr = {"Vodka",  "Beer", "Mulled wine", "Empty"};
    private Integer[] testInt = {R.drawable.bottle_one, R.drawable.bottle_two, R.drawable.bottle_three, R.drawable.emptybottle};
    TextView dialogText;
    TextView alcoholrate;
    TextView cabinet;
    ImageView dialogImage;
    Button removeButton;



    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.drinkscapinet, container, false);
            setHasOptionsMenu(true);


        cabinet = (TextView)rootView.findViewById(R.id.textCabinet);
        cabinet.setTextColor(getResources().getColor(R.color.ColorWhite));

        Log.i("Error", HelperClass.Name.YourName);

        if(HelperClass.Name.YourName.endsWith("s")){
            cabinet.setText(HelperClass.Name.YourName + " " + "Liquor Cabinet");
        }else{
            cabinet.setText(HelperClass.Name.YourName + "'s Liquor Cabinet");

        }

        dataSet = new Dataset();

        /*for(int i = 0; i<=2; i++){
            Cocktail listItem = new Cocktail();
            int rand = (int) (Math.random()*3);
            listItem.setName(testStr[rand]);
            listItem.setImageId(testInt[rand]);
            Log.i("gridlistitems", testInt[rand]+"-"+testStr[rand]);
            gridArray.add(listItem);
        }*/

        ArrayList<Categories> categories = APIManager.getCategories();

        for(Categories cat : categories){
            ArrayList<Categories> catitem = cat.getCategoryList();
            for(Categories catitemIncat : catitem){
                Log.i("Cat in cat", catitemIncat.getName()+" "+catitemIncat.getId());
                ArrayList<Ingredient> ingredientsinCat = APIManager.getIngridient(catitemIncat.getId());
                dataSet.addSection(catitemIncat.getName(), ingredientsinCat);
            }
        }
        cursorMap = dataSet.getSectionCursorMap();



        listView = (ListView) rootView.findViewById(R.id.listview);
        listView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        listView.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);

                        // now check the width of the list view
                        int width = listView.getWidth();

                        adapter = new SectionedGridViewAdapter(
                                getActivity(), cursorMap, listView
                                .getWidth(), listView.getHeight(),
                                getResources().getDimensionPixelSize(
                                        R.dimen.grid_item_size));

                        adapter.setListener(DrinksCabinet.this);
                        listView.setAdapter(adapter);

                        listView.setDividerHeight(adapter
                                .gapBetweenChildrenInRow());

                    }
                });




            //drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);


        cabinetManager = new CabinetManager();
        /*emptyGridAdapter = new CustomGridViewAdapter(this.getActivity(), R.layout.row_grid, emptygridArray);
        vodkaGridAdapter = new CustomGridViewAdapter(this.getActivity(), R.layout.row_grid, gridArray);
        vodkaGridView.setAdapter(vodkaGridAdapter);
        vodkaGridView.setOnItemClickListener(this);*/



            return rootView;
        }



//        @Override
        public void onClick(View v) {

        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*if(parent == vodkaGridView){
            selectedItem = position;
            String text = gridArray.get(selectedItem).getName();
            int imageId = gridArray.get(selectedItem).getImageId();
            dialog(text,imageId);
            //Toast.makeText(getActivity(), gridArray.get(position).getName(), Toast.LENGTH_SHORT).show();
        }*/
    }
       @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.drinkscabinetmenu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToCabinet:
                Fragment newFragment = new addToCabinet();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            default:
                break;
        }
        return false;
    }



    public void dialog(String text, int imageID){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.drinkcabinetdialog);
        dialog.setTitle(text);
        dialog.setCancelable(true);

        dialogText = (TextView) dialog.findViewById(R.id.dialogText);
        alcoholrate = (TextView) dialog.findViewById(R.id.alcoholrate);
        dialogImage = (ImageView) dialog.findViewById(R.id.dialogImage);
        removeButton = (Button) dialog.findViewById(R.id.buttonRemove);

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("gridarray remove", selectedItem+"");

                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
                gridArray.remove(selectedItem);
                dialog.dismiss();
            }
        });

        dialogText.setText(text);
        alcoholrate.setText("99%");
        dialogImage.setImageResource(imageID);

        dialog.show();

    }

    @Override
    public void onGridItemClicked(String sectionName, int position, View v) {

        Cursor sectionCursor = cursorMap.get(sectionName);
        if(sectionCursor.moveToPosition(position)) {
            selectedItem = position;
            String text = gridArray.get(selectedItem).getName();
            int imageId = gridArray.get(selectedItem).getImageId();
            String data = sectionCursor.getString(0);
            String msg = sectionName + " - Item clicked is:" + data;
            dialog(position+" - "+data,imageId);
            Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg);
        }
    }
}
