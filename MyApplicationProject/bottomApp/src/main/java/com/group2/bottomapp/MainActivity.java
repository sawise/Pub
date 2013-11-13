package com.group2.bottomapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
    public static final String POSITION = "POSITION";
    final String[] menuTitle = {"Drinks cabinet", "Drinks", "Random", "About","Add","Drink"};
    final String[] fragments = {
            "com.group2.bottomapp.DrinksCabinet",
            "com.group2.bottomapp.Drinks",
            "com.group2.bottomapp.Random",
            "com.group2.bottomapp.About",
            "com.group2.bottomapp.AddToCabinet",
            "com.group2.bottomapp.Drink"
    };
    private int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuTitle);

        final DrawerLayout drawer = (DrawerLayout) findViewById((R.id.drawerLayout));
        final ListView navList = (ListView) findViewById(R.id.drawer);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[0]));
        tx.commit();

        navList.setAdapter(adapter);

        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                currentPos = pos;
                getActionBar().setTitle(menuTitle[pos]);
                drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[pos]));
                        tx.commit();
                    }
                });
                drawer.closeDrawer(navList);
            }
        });

    }




}