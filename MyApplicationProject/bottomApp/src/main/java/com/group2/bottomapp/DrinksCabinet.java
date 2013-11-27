package com.group2.bottomapp;

    import android.app.Dialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DrinksCabinet extends Fragment implements View.OnClickListener, GridView.OnItemClickListener, GridView.OnItemLongClickListener {


    //public ArrayList<HashMap<Integer, String>> drinkListt = new ArrayList<HashMap<Integer, String>>();
    private GridView drinkGridView;
    private  ArrayList<Cocktail> gridArray = new ArrayList<Cocktail>();
    private CustomGridViewAdapter customGridAdapter;
    private CabinetManager cabinetManager;
    private int selectedItem;
    private String[] testStr = {"Vodka",  "Beer", "Mulled wine"};
    private Integer[] testInt = {R.drawable.bottle_one, R.drawable.bottle_two, R.drawable.bottle_three};
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
            drinkGridView = (GridView) rootView.findViewById(R.id.drinkGridView);
        cabinet = (TextView)rootView.findViewById(R.id.textCabinet);
        if(HelperClass.Name.YourName.endsWith("s")){
            cabinet.setText(HelperClass.Name.YourName + " " + "Liquor Cabinet");
        }else{
            cabinet.setText(HelperClass.Name.YourName + "'s Liquor Cabinet");
        }

        cabinetManager = new CabinetManager();
            customGridAdapter = new CustomGridViewAdapter(this.getActivity(), R.layout.row_grid, gridArray);
            drinkGridView.setAdapter(customGridAdapter);
            drinkGridView.setOnItemClickListener(this);
            drinkGridView.setOnItemLongClickListener(this);


            for(int i = 0; i<=100; i++){
                Cocktail listItem = new Cocktail();
                int rand = (int) (Math.random()*3);
                listItem.setName(testStr[rand]);
                listItem.setImageId(testInt[rand]);
                Log.i("gridlistitems", testInt[rand]+"-"+testStr[rand]);
                gridArray.add(listItem);
            }

            return rootView;
        }



        @Override
        public void onClick(View v) {

        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == drinkGridView){
            selectedItem = position;
            String text = gridArray.get(selectedItem).getName();
            int imageId = gridArray.get(selectedItem).getImageId();
            dialog(text,imageId);
            //Toast.makeText(getActivity(), gridArray.get(position).getName(), Toast.LENGTH_SHORT).show();
        }
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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == drinkGridView){
            Log.i("gridview", l + "<->" + i);

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
                customGridAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialogText.setText(text);
        alcoholrate.setText("99%");
        dialogImage.setImageResource(imageID);

        dialog.show();

    }
}
