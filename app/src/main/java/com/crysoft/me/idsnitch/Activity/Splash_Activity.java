package com.crysoft.me.idsnitch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crysoft.me.idsnitch.MainActivity;

public class Splash_Activity extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check for first time launch
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()){

            startActivity(new Intent(this,MainActivity.class));
            finish();

        }else {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }


    }

}
