package com.group2.bottomapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity implements View.OnClickListener {
    private Button regBtn;
    private Button loginBtn;
    private Button fbRegBtn;
    private EditText inputEmail;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        regBtn = (Button)findViewById(R.id.btnReg);
        fbRegBtn = (Button)findViewById(R.id.btnFB);
        inputEmail = (EditText)findViewById(R.id.emailInput);
        inputPassword = (EditText)findViewById(R.id.passwordInput);
        regBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        fbRegBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == regBtn){
            Intent i = new Intent(getApplicationContext(), register.class);
            startActivity(i);
    }
        else if (v == loginBtn){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}