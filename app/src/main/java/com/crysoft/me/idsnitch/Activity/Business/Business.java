package com.crysoft.me.idsnitch.Activity.Business;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crysoft.me.idsnitch.Activity.Professional.Professional;
import com.crysoft.me.idsnitch.MainActivity;
import com.crysoft.me.idsnitch.R;

public class Business extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void goToMain(){
        Intent intent = new Intent(Business.this, MainActivity.class);
        startActivity(intent);
    }


}