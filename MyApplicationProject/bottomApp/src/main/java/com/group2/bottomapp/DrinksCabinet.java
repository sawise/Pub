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
import android.widget.ListAdapter;
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
    private ListAdapter la;
    private int selectedItem;
    private TextView dialogText;
    private TextView alcoholrate;
    private TextView cabinet;
    private ImageView dialogImage;
    private Button removeButton;
    private Button cancelButton;



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

        ArrayList<Categories> categories = APIManager.getCategories();

        for(Categories cat : categories){
                Log.i("Catt", cat.getName()+" "+cat.getId());
                ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByCategory(cat.getId());
                dataSet.addSection(cat.getName(), ingredientsinCat);
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


           return rootView;
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
        cancelButton = (Button) dialog.findViewById(R.id.buttonCancel);

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("gridarray remove", selectedItem+"");
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
                adapter.remove(1);
                adapter.notifyDataSetChanged ();
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        Log.d("gridd", sectionName+"<->"+sectionCursor);

        if(sectionCursor.moveToPosition(position)) {
            String data = sectionCursor.getString(0);
            String msg = "Item clicked is:" + data;

            Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
            Log.d("gridd", "" + sectionName);
            //adapter.
            dialog(data, R.drawable.bottle_one);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
