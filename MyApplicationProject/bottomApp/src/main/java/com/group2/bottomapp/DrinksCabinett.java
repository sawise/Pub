package com.group2.bottomapp;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.MatrixCursor;
import android.os.Handler;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DrinksCabinett extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {

    private  ArrayList<Ingredient> gridArray = new ArrayList<Ingredient>();
    private CustomGridViewAdapter adapter;
    LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter;
    private GridView drinkGridView;
    private int selectedItem;
    private TextView cabinet;
    private TextView dialogText;
    private ImageView dialogImage;
    private Button removeButton;
    private Button cancelButton;
    private int retries = 0;
    private int allowedRetries = 10;
    private boolean stopRetrying = false;

    private ProgressDialog progress;
    ArrayList<Categories> categories;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drinks_cabinett, container, false);
        setHasOptionsMenu(true);
        cabinet = (TextView)rootView.findViewById(R.id.textCabinett);
        cabinet.setTextColor(getResources().getColor(R.color.ColorWhite));

        if(HelperClass.User.userName.endsWith("s")){
            cabinet.setText(HelperClass.User.userName + " " + "Liquor Cabinet");
        }else{
            cabinet.setText(HelperClass.User.userName + "'s Liquor Cabinet");
        }

        drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.show();

        categories = APIManager.getCategories();
        //LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter = new LinkedHashMap<String, ArrayList<Ingredient>>();

        String catStr = "";
        adapter = new CustomGridViewAdapter(this.getActivity(), R.layout.row_grid, gridArray);
        drinkGridView.setAdapter(adapter);

        ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByUser(HelperClass.User.userId);
        if(!ingredientsinCat.isEmpty()) {
            Collections.sort(ingredientsinCat, new Comparator<Ingredient>() {
                @Override
                public int compare(Ingredient ingredient, Ingredient ingredient2) {
                    Log.i("compare", ingredient.getCategoryID()+"<->"+ingredient2.getCategoryID());
                    return ingredient.getCategoryID();

                }
            });
            for(Categories cat : categories){
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
            }
            progress.dismiss();
        } else {
            startRefreshThread();
        }

        //Log.i("getSec", dataSett.getSections());
        //LinkedHashMap<String, ArrayList<Ingredient>> sendToAdapter = dataSett.getSections();


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
            String category = gridArray.get(position).getCategoryName();
            //int imageId = gridArray.get(position).getImageId();
            dialog(text,R.drawable.bottle_three, category);
            Toast.makeText(getActivity(), "Item: " + gridArray.get(position).getName(), Toast.LENGTH_LONG).show();
        }
    }

    public void dialog(String text, int imageID, String category){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.drinkcabinetdialog);
        dialog.setTitle(category);
        dialog.setCancelable(true);

        dialogText = (TextView) dialog.findViewById(R.id.dialogText);
        dialogImage = (ImageView) dialog.findViewById(R.id.dialogImage);
        removeButton = (Button) dialog.findViewById(R.id.buttonRemove);
        cancelButton = (Button) dialog.findViewById(R.id.buttonCancel);

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("gridarray remove", selectedItem+"");
                APIManager.removeIngredientfromAccount(gridArray.get(selectedItem).getId());
                Toast.makeText(getActivity(), "Sucessfully removed: "+gridArray.get(selectedItem).getName(), Toast.LENGTH_SHORT).show();
                gridArray.remove(selectedItem);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                startRefreshThread();
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
    private void startRefreshThread(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                do {
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {

                            Log.i("retry...", "no =)");
                            categories = APIManager.getCategories();
                            adapter = new CustomGridViewAdapter(getActivity(), R.layout.row_grid, gridArray);

                            if (!categories.isEmpty()) {
                                ArrayList<Ingredient> ingredientsinCat = APIManager.getIngredientsByUser(HelperClass.User.userId);
                                for(Categories cat : categories){
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
                                }
                                drinkGridView.setAdapter(adapter);
                                progress.dismiss();
                            } else {
                                Log.i("retry...", "yes o.O");
                                retries++;
                                if(retries < allowedRetries){
                                    stopRetrying = true;
                                    progress.dismiss();
                                }
                            }
                        }
                    });
                } while (categories.isEmpty() && !stopRetrying);
            }
        };
        new Thread(runnable).start();
    }
}
