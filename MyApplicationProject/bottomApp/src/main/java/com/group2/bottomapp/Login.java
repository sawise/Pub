package com.group2.bottomapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
    public void finishActivity(String response){
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(this, response, 1000).show();
        startActivity(in);
//        set = getSharedPreferences("assignmentKey", MODE_PRIVATE).getStringSet("myKey", null);

        // add the email and password to sharedprefs
        //getSharedPreferences("bottomAppUser", MODE_PRIVATE).edit().putString("email", iEmail.getText().toString()).commit();
        //getSharedPreferences("bottomAppPass", MODE_PRIVATE).edit().putString("password", iPassword.getText().toString()).commit();

        this.finish();
    }

    // show the error dialog
    public void showErrorDialog(String message){
        // if an error occured

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

                JsonLoginPoster jLoginPoster = new JsonLoginPoster(getApplicationContext(), this, userEmail, userPass);

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
                Intent fb = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(fb);
                finish();
        }
    }
}


// TODO keep login state when logged in once
// TODO if no internet show dialog

/*
*     // show the error dialog
    private void showErrorDialog(){
        // if an error occured

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
* */