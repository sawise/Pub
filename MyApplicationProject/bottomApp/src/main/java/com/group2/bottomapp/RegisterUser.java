package com.group2.bottomapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterUser extends Activity implements View.OnClickListener {

    private Button registerBtn;
    private Button oLogin;
    public  EditText iName;
    private EditText iEmail;
    private EditText iPassword;
    private TextView textEmail;
    private TextView textPass;
    private TextView textName;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        registerBtn = (Button)findViewById(R.id.btnRegister);
        oLogin = (Button)findViewById(R.id.openLogin);
        iName = (EditText)findViewById(R.id.reg_name);
        iEmail = (EditText)findViewById(R.id.reg_email);
        iPassword = (EditText)findViewById(R.id.reg_password);
        textEmail = (TextView)findViewById(R.id.emailText);
        textPass = (TextView)findViewById(R.id.passText);
        textName = (TextView)findViewById(R.id.nameText);
        registerBtn.setOnClickListener(this);
        oLogin.setOnClickListener(this);
        getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void onClick(View v) {
        String password = iPassword.getText().toString();
        String email = iEmail.getText().toString();
        String name = iName.getText().toString();
        HelperClass.Name.YourName = name;
        HelperClass.Name.YourEmail = email;

        if (v == registerBtn){

            LoginValidator loginValidator = new LoginValidator();
            if (name.length() <2) {
                Toast.makeText(this, "Your Name must be 2-11 chars", 1000).show();
                textName.setTextColor(getResources().getColor(R.color.ColorRed));
            } else if (name.length() >11){
                Toast.makeText(this, "Your Name must be 2-11 chars", 1000).show();
                textName.setTextColor(getResources().getColor(R.color.ColorRed));
            } else if(!loginValidator.validate(email)) {
                textName.setTextColor(getResources().getColor(R.color.ColorWhite));
                Toast.makeText(this, "thats not a email adress", 1000).show();
                textEmail.setTextColor(getResources().getColor(R.color.ColorRed));
            } else if(password.length() <5){
                textEmail.setTextColor(getResources().getColor(R.color.ColorWhite));
                Toast.makeText(this, "Your password must be atleast 5 chars", 1000).show();
                textPass.setTextColor(getResources().getColor(R.color.ColorRed));

            } else {
                JsonPoster jPoster = new JsonPoster(getApplicationContext(), this, name, email, password);

                // if we are connected
                if(jPoster.isConnected() != false)
                {
                    jPoster.PostJson();
                }

            }


        }
        else if (v == oLogin){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
    }

}