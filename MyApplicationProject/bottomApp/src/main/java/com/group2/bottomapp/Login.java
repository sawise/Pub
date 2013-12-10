package com.group2.bottomapp;

import android.app.Activity;
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
            //if (db.userEmail && db.userPass == true){
            if (!validEmail.validate(userEmail)){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(this, "Welcome " + HelperClass.Name.YourName, 1000).show();
            /*} else {
                Toast.makeText(this, "Your Email or Password is wrong", 1000);
                textUserEmail.setTextColor(getResources().getColor(R.color.ColorRed));
                textUserPass.setTextColor(getResources().getColor(R.color.ColorRed));
            }*/
            }
        }
        else if (v == fbRegBtn){
            //if (fb.userEmail && fb.userPass == true){
                Intent fb = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(fb);
                finish();
            //}
        }
    }
}