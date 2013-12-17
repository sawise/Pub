package com.group2.bottomapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity implements View.OnClickListener {
    private Button regBtn;
    private Button loginBtn;
    private Button fbRegBtn;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView textUserEmail;
    private TextView textUserPass;
    private ProgressDialog progressDialog;
    private JsonLoginPoster jLoginPoster = null;
    private JsonIdentifierLoginPoster jLoginIdentifierPoster = null;
    private String storedEmail = null;
    private String storedIdentifier = null;
    private TextView or;
    private Crypto crypto = new Crypto();
    private String key = "b3Oto7m55Az00pp7g6fd5ds";
    //private SoundHelper soundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        loginBtn.requestFocus();
        regBtn = (Button)findViewById(R.id.btnReg);
        fbRegBtn = (Button)findViewById(R.id.btnFB);
        inputEmail = (EditText)findViewById(R.id.emailInput);
        inputPassword = (EditText)findViewById(R.id.passwordInput);
        textUserEmail = (TextView)findViewById(R.id.email);
        textUserPass = (TextView)findViewById(R.id.password);
        regBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        fbRegBtn.setOnClickListener(this);
        or = (TextView)findViewById(R.id.of);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        fbRegBtn.setVisibility(View.INVISIBLE);
        or.setVisibility(View.INVISIBLE);

        try{
            Intent intent = getIntent();

            inputEmail.setText(intent.getStringExtra("userEmail"));
            inputPassword.setText(intent.getStringExtra("userPassword"));
        } catch (Exception ex) {
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i("onResume", "HERE");

        if(isConnected() != true){
            showErrorDialog();
        } else {

            try{
                storedEmail = getSharedPreferences("bottomAppUser", MODE_PRIVATE).getString("email", null);
                storedIdentifier = getSharedPreferences("bottomAppIdentifier", MODE_PRIVATE).getString("identifier", null);

                // decrypt
                storedIdentifier = crypto.decrypt(key, storedIdentifier);


            } catch (Exception ex) {
                Log.e("BottomApp-Exception", ex.getMessage());
            }


            if(storedEmail != null && storedIdentifier != null){
                if(storedEmail != "" && storedIdentifier != ""){
                    jLoginIdentifierPoster = new JsonIdentifierLoginPoster(getApplicationContext(), this, storedEmail, storedIdentifier);

                    jLoginIdentifierPoster.PostJson();
                }
            }
        }
    }

    // save to shared prefs
    public void saveValues(){
        try{
            // encrypt password
            String encryptedPassword = crypto.encrypt(key, HelperClass.User.userIdentifier);
            // add the email and password to shared prefs
            getSharedPreferences("bottomAppUser", MODE_PRIVATE).edit().putString("email", HelperClass.User.userEmail).commit();
            getSharedPreferences("bottomAppIdentifier", MODE_PRIVATE).edit().putString("identifier", encryptedPassword).commit();

        } catch (Exception ex){
            Log.e("Exception: ", ex.getMessage());
        }
    }

    // show loading dialog
    public void showProgressDialog(){
        if(progressDialog ==  null){
            // display dialog when loading data
            progressDialog = ProgressDialog.show(this, "Loading", "Please Wait...", true, false);
        }
    }

    // hide loading dialog
    public void hideProgressDialog(){
        // if loading dialog is visible, then hide it
        if(progressDialog != null){
            progressDialog.cancel();
        }
    }


    // finish activity with a feedback toast
    public void finishActivity(String result){

        if(inputEmail.getText().toString().length() > 5){
            HelperClass.User.userEmail = inputEmail.getText().toString();
        }

        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, result, 1000).show();
        startActivity(in);
        this.finish();
    }

    // show the error dialog
    public void showErrorDialog(String message){
        // if an error occurred

        // set progress dialog to null
        progressDialog = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("An Error has occurred!");
        builder.setMessage(message);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onClick(View v) {
        String userEmail = inputEmail.getText().toString();
        String userPass = inputPassword.getText().toString();
        LoginValidator validEmail = new LoginValidator();

        if (v == regBtn){
            Intent i = new Intent(getApplicationContext(), RegisterUser.class);
            startActivity(i);
            finish();
    }
        else if (v == loginBtn){
            if (validEmail.validate(userEmail)){

                jLoginPoster = new JsonLoginPoster(getApplicationContext(), this, userEmail, userPass);

                // if we are connected
                if(jLoginPoster.isConnected() != false)
                {
                    jLoginPoster.PostJson();
                }

            } else {
                Toast.makeText(this, "Your Email or Password is wrong", 1000);
                textUserEmail.setTextColor(getResources().getColor(R.color.ColorRed));
                textUserPass.setTextColor(getResources().getColor(R.color.ColorRed));
            }

        }
        else if (v == fbRegBtn){
            //Intent fb = new Intent(getApplicationContext(), Test.class);
            //startActivity(fb);
            //finish();
        }
    }

    private boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        } else {
            return false;
        }

    }

    // show the error dialog
    private void showErrorDialog(){
        // if an error occurred

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("You need internet for the app to work!");
        builder.setMessage("Please turn on your internet connection!");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}