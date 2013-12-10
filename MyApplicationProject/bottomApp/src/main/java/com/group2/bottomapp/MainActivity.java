package com.group2.bottomapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
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

    private static Context context;

    private ActionBarDrawerToggle menuToggle;
    public static final String POSITION = "POSITION";
    final String[] menuTitle = {"Favorites", "Cocktails","Liquor Cabinet", "Random Cocktail", "Shot Race", "About"};
    final int[] menuImage = new int[] {R.drawable.favorits_pic, R.drawable.cocktail_pic, R.drawable.cabinet_pic, R.drawable.random_pic, R.drawable.shotl_pic, R.drawable.aboutl_pic};
    final String[] fragments = {
            "com.group2.bottomapp.Favorites",
            "com.group2.bottomapp.Drinks",
            "com.group2.bottomapp.DrinksCabinet",
            "com.group2.bottomapp.RandomDrink",
            "com.group2.bottomapp.ShotRace",
            "com.group2.bottomapp.About"
    };
    private int currentPos;
    private int oldPos;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MainActivity.context = getApplicationContext();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuTitle);

        final DrawerLayout drawer = (DrawerLayout) findViewById((R.id.drawerLayout));
        final ListView navList = (ListView) findViewById(R.id.drawer);
        menuToggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.menu, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(menuToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        //getActionBar().setHomeButtonEnabled(true);


        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[0]));
        tx.commit();

        navList.setAdapter(adapter);

        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                currentPos = pos;
                getActionBar().setTitle(menuTitle[pos]);
                getActionBar().setIcon(menuImage[pos]);
                menuToggle.syncState();
                drawer.closeDrawer(navList);

                //Clears backstack
                FragmentManager fm = getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                if(currentPos != oldPos){
                    FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[pos]));
                    menuToggle.syncState();
                    tx.commit();
                    oldPos = currentPos;
                }

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
    protected void onSaveInstanceState(Bundle outState){

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

}