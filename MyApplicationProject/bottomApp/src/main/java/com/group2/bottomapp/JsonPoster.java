package com.group2.bottomapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Svempa on 2013-12-04.
 */
public class JsonPoster {

    private Context context;
    private User user;
    private String username;
    private String email;
    private String password;
    private RegisterUser callback;

    public JsonPoster(Context context, RegisterUser callback, String username, String email, String password){
        this.context = context;
        this.username = username;
        this.email = email;
        this.password = password;
        this.callback = callback;
    }

    public void PostJson(){
        callback.showProgressDialog();
        Log.i("Dialog", "Not showing?");
        new HttpAsyncTask().execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/new");
    }


    public static String POSTUSER(String url, User user){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", user.getUsername());
            jsonObject.accumulate("email", user.getEmail());
            jsonObject.accumulate("password", user.getPassword());

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", "apikey='1c9fk3u35ldcefgw'");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            result = "Did not work!";
        }

        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            return POSTUSER(urls[0], user);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result.contains("User added successfully")){
                callback.hideProgressDialog();
                callback.finishActivity("User added successfully");
            } else if(result.contains("Email is in use!")){
                callback.hideProgressDialog();
                callback.showErrorDialog("The email is already in use! Please try another");
            } else if(result.contains("Internal error, The User could not be added!")){
                callback.hideProgressDialog();
                callback.showErrorDialog("A server error has occurred!");
            } else if(result.contains("Did not work!")){
                callback.hideProgressDialog();
            }
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
