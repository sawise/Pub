package com.group2.bottomapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class register extends Activity implements View.OnClickListener {
private Button registerBtn;
private Button oLogin;
private EditText iName;
private EditText iEmail;
private EditText iPassword;
private TextView textEmail;
private TextView textPass;
private TextView textName;

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

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {
        String password = iPassword.getText().toString();
        String email = iEmail.getText().toString();
        String name = iName.getText().toString();

        if (v == registerBtn){

            LoginValidator loginValidator = new LoginValidator();
            if (name.length() <2){
                Toast.makeText(this, "Your Name must be atleast 2 chars", 1000).show();
                textName.setTextColor(getResources().getColor(R.color.ColorRed));
            }
            else if(!loginValidator.validate(email)) {
                textName.setTextColor(getResources().getColor(R.color.ColorWhite));
                Toast.makeText(this, "thats not a email adress", 1000).show();
                textEmail.setTextColor(getResources().getColor(R.color.ColorRed));
            }else if(password.length() <5){
                textEmail.setTextColor(getResources().getColor(R.color.ColorWhite));
                Toast.makeText(this, "Your password must be atleast 5 chars", 1000).show();
                textPass.setTextColor(getResources().getColor(R.color.ColorRed));

            } else {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                Toast.makeText(this, "Account created", 1000).show();
            }


        }
        else if (v == oLogin){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}