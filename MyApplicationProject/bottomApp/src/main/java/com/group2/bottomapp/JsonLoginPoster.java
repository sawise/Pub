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
 * Created by Svempa on 2013-12-10.
 */
public class JsonLoginPoster {

    private Context context;
    private User user;
    private String email;
    private String password;
    private Login callback;

    public JsonLoginPoster(Context context, Login callback, String email, String password){
        this.context = context;
        this.email = email;
        this.password = password;
        this.callback = callback;
    }

    public void PostJson(){
        callback.showProgressDialog();
        new HttpAsyncTask().execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/login");
    }


    public static String POSTLOGIN(String url, User user){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            JSONObject jsonObject = new JSONObject();
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
            user.setEmail(email);
            user.setPassword(password);

            return POSTLOGIN(urls[0], user);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            HelperClass.Name.setValues(result);

            if(result.contains("Logged in successfully!")){
                callback.hideProgressDialog();
                callback.finishActivity(result);
            } else if(result.contains("Login failed!")){
                callback.hideProgressDialog();
                callback.showErrorDialog("Login failed, wrong email or password!");
            } else if(result.contains("Internal Error")){
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


    //TODO use response codes: httpResponse.getStatusLine().getStatusCode();
    //TODO send the encrypted version to server
    //TODO use password-token instead: http://android-developers.blogspot.ca/2013/01/verifying-back-end-calls-from-android.html?m=1

}
