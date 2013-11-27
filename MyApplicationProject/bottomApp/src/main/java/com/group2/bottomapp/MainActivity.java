package com.group2.bottomapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
    private ActionBarDrawerToggle menuToggle;
    public static final String POSITION = "POSITION";
    final String[] menuTitle = {"Login","Liquor Cabinet", "Drinks", "Random", "About"};
    final String[] fragments = {
            "com.group2.bottomapp.DrinksCabinet",
            "com.group2.bottomapp.Drinks",
            "com.group2.bottomapp.RandomDrink",
            "com.group2.bottomapp.About"
    };
    private int currentPos;
    //private SoundEffect soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //soundEffect = new SoundEffect();
        //soundEffect.start(R.raw.startup, this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuTitle);

        final DrawerLayout drawer = (DrawerLayout) findViewById((R.id.drawerLayout));
        final ListView navList = (ListView) findViewById(R.id.drawer);
        menuToggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.menu, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(menuToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

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
                        menuToggle.syncState();
                        tx.commit();
                    }
                });
                menuToggle.syncState();
                drawer.closeDrawer(navList);

            }
        });

        CabinetManager.Initiate(getApplication());
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        menuToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        menuToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    /*protected void onSaveInstanceState(Bundle outState){

    }*/

}