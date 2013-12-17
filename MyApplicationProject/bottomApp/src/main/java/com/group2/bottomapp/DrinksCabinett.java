package com.group2.bottomapp;


import android.app.Dialog;
import android.database.MatrixCursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DrinksCabinett extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {

    private List<Integer> drinkList;
    private Integer[] drinkArray;
    private  ArrayList<Ingredient> gridArray = new ArrayList<Ingredient>();
    private CustomGridViewAdapter adapter;
    LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter;
    private GridView drinkGridView;
    private Datasett dataSett;
    private int selectedItem;
    TextView dialogText;
    TextView alcoholrate;
    ImageView dialogImage;
    Button removeButton;
    Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drinks_cabinett, container, false);
        setHasOptionsMenu(true);
        dataSett = new Datasett();

        ArrayList<Categories> categories = APIManager.getCategories();
        //LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter = new LinkedHashMap<String, ArrayList<Ingredient>>();

        String catStr = "";
        Log.i("userid", HelperClass.User.userId+" - "+HelperClass.User.userName);
        ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByUser(HelperClass.User.userId);
        for(Categories cat : categories){
            //add LayoutParams
            ArrayList<Ingredient> arrayInGrid = new ArrayList<Ingredient>();
            //ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByCategory(cat.getId());
            for(Ingredient ingIncat : ingredientsinCat){
                if(cat.getId() == ingIncat.getCategoryID()){
                    Ingredient ingtoAdd = new Ingredient();
                    ingtoAdd.setId(ingIncat.getId());
                    ingtoAdd.setName(ingIncat.getName());
                    ingtoAdd.setCategoryName(ingIncat.getCategoryName());
                    ingtoAdd.setCategoryID(ingIncat.getCategoryID());
                    ingtoAdd.setMeasurement(ingIncat.getMeasurement());
                    gridArray.add(ingtoAdd);
                }
            }
            //gridArray.add(arrayInGrid);
            catStr += cat.getName()+",";
            //dataSett.addSection(cat.getName(), ingredientsinCat);
            //sendToAdapter.put(cat.getName(),ingredientsinCat);
        }

        //Log.i("getSec", dataSett.getSections());
        //LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter = dataSett.getSections();
        drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);
        drinkList = new ArrayList<Integer>();


        drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);

        adapter = new CustomGridViewAdapter(this.getActivity(), R.layout.row_grid, gridArray);
        drinkGridView.setAdapter(adapter);
        drinkGridView.setOnItemClickListener(this);

        return rootView;
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == drinkGridView){
            selectedItem = position;
            String text = gridArray.get(position).getName();
            //int imageId = gridArray.get(position).getImageId();
            dialog(text,R.drawable.bottle_three);
            Toast.makeText(getActivity(), "Item: " + gridArray.get(position).getName(), Toast.LENGTH_LONG).show();
        }
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
                gridArray.remove(selectedItem);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogText.setText(text);
        dialogImage.setImageResource(imageID);

        dialog.show();

    }
}
