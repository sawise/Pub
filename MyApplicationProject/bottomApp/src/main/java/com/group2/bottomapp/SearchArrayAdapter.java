package com.group2.bottomapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Svempa on 2013-12-18.
 */
public class SearchArrayAdapter extends ArrayAdapter<Ingredient> implements Filterable {

    private final Context context;
    private List<Ingredient> ingredientList;
    private List<Ingredient> origData;
    private Ingredient ingredient;
    private LruCache<String, Bitmap> mMemoryCache;

    public SearchArrayAdapter(Context context, List<Ingredient> ingredientList){
        super(context, R.layout.search_list_layout, ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
        this.origData = ingredientList;


        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number
                // of items.
                return bitmap.getByteCount();
            }

        };


    }


    // static class to hold the view
    static class ViewHolder{
        ImageView ingredientImage;
        TextView ingredientNameText;
        Button addIngredientButton;
    }


    @Override
    public int getCount() {
        return (ingredientList == null) ? 0 : ingredientList.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // adds the ingredient to the cabinet
    private void addToCabinet(final Ingredient ingredient){
        APIManager.addIngredientToAccount(ingredient.getId());

        addToCabinet.handler.post(new Runnable() {
            @Override
            public void run() {

                new Thread() {
                    public void run() {
                        while(!(addToCabinet.loadStatus == "done" || addToCabinet.loadStatus == "fail")){
                            try {
                                sleep(200);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("tjafs", addToCabinet.loadStatus);
                        if(addToCabinet.loadStatus == "done"){
                            addToCabinet.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.getAppContext(), ingredient.getName() + " was added to cabinet", 1000).show();
                                    addToCabinet.loadStatus = "";
                                }
                            });
                        } else if(addToCabinet.loadStatus == "fail"){
                            addToCabinet.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.getAppContext(), "Failed to add " + ingredient.getName() + " to cabinet\nMaybe you already have the ingredient?", 1000).show();
                                    addToCabinet.loadStatus = "";
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }


    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        ViewHolder holder;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.search_list_layout, parent, false);

            holder = new ViewHolder();

            holder.ingredientNameText = (TextView) v.findViewById(R.id.ingredient_name);
            holder.ingredientImage = (ImageView) v.findViewById(R.id.ingredient_image);
            holder.addIngredientButton = (Button) v.findViewById(R.id.btnIngredientAdd);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }



        // get ingredient
        ingredient = ingredientList.get(position);

        // set ingredient text and image
        holder.ingredientNameText.setText(ingredient.getName());

        loadBitmap(ingredient.imageResourceId, holder.ingredientImage);

        holder.addIngredientButton.setTag(ingredient);

        // set onclick and handle onclick
        holder.addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Ingredient selectedIngredient  = (Ingredient)v.getTag();
                addToCabinet(selectedIngredient);
            }
        });

        return v;
    }


    // Implements Filterable and the filtering is done below.
    // Need to override the object Ingredient .toString method as well

    @Override
    public Filter getFilter(){
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<Ingredient> founded = new ArrayList<Ingredient>();
                    for(Ingredient item: ingredientList){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                } else {
                    result.values = origData;
                    result.count = origData.size();
                }
                return result;


            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Now we have to inform the adapter about the new list filtered
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else {
                    ingredientList = (List<Ingredient>) results.values;
                    notifyDataSetChanged();
                }
            }

        };
    }


    // all below to load and cache images in async tasks

    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            //imageView.setBackgroundResource(R.drawable.ic_non);
            task.execute(resId);
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                    bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was
        // cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) mMemoryCache.get(key);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        public int data = 0;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            final Bitmap bitmap = decodeSampledBitmapFromResource(
                    context.getResources(), data, 50, 50);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }





    // TODO handle server timeout??
}




