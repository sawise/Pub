package com.group2.bottomapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Login extends Fragment implements View.OnClickListener {
    private Button regBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_activity, container, false);
        final Button loginBtn = (Button)rootView.findViewById(R.id.btnLogin);
        regBtn = (Button)rootView.findViewById(R.id.btnReg);
        final Button fbRegBtn = (Button)rootView.findViewById(R.id.btnFB);
        regBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == regBtn){
            Log.i("hej","hej");
        Intent i = new Intent(getActivity().getApplicationContext(), register.class);
        startActivity(i);
    }
    }
}