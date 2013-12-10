package com.group2.bottomapp;

/**
 * Created by sam on 12/4/13.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

public class Dataset {

    private LinkedHashMap<String, ArrayList<Ingredient>> sectionItems = new LinkedHashMap<String, ArrayList<Ingredient>>();

    public static final String DATA_COLUMN = "data";

    public static final int TYPE_DATA = 1;

    public static final String ITEM_PREFIX = "data-";

    public static final String[] COLUMNS = new String[] { DATA_COLUMN, "_id" };

    private static volatile int INDEX = 1;

    private LinkedHashMap<String, Cursor> sectionCursors = new LinkedHashMap<String, Cursor>();

    public void addSection(String sectionName, ArrayList<Ingredient> arrayList) {
        sectionItems.put(sectionName, arrayList);
    }

    public Cursor getSectionCursor(String sectionName) {
        MatrixCursor cursor = (MatrixCursor) sectionCursors.get(sectionName);
        if( cursor == null) {
            cursor = new MatrixCursor(COLUMNS);
            //int items = sectionItems.get(sectionName);

            // now add item rows
            Iterator<Map.Entry<String, ArrayList<Ingredient>>> itr = sectionItems.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, ArrayList<Ingredient>> entry = itr.next();
                String key = entry.getKey();
                Log.i("valuee", "key: " + key);

                for(Ingredient listitem : entry.getValue()){
                    String itemname = listitem.getName();
                    cursor.addRow(new Object[] { itemname, INDEX++ });
                    Log.i("valuee", "value: " + itemname);
                }

            }

           /* for( String listitem : sectionItems.){
                cursor.addRow(new Object[] { listitem, INDEX++ });
                Log.i("listitemss", listitem);
            }
            for (int i = 0; i < items; i++) {

            }*/

            sectionCursors.put(sectionName, cursor);

        }

        return cursor;
    }

    public LinkedHashMap<String, Cursor> getSectionCursorMap() {
        if(sectionCursors.isEmpty()) {
            for(String sectionName : sectionItems.keySet()) {
                getSectionCursor(sectionName);
            }
        }


        return sectionCursors;

    }

}
