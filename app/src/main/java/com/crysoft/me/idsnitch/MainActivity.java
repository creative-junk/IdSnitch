package com.crysoft.me.idsnitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.crysoft.me.idsnitch.Activity.Business.Business;
import com.crysoft.me.idsnitch.Activity.Product.Product;
import com.crysoft.me.idsnitch.Activity.Professional.Professional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToProfessional(View view){
        Intent intent = new Intent(MainActivity.this,Professional.class);
        startActivity(intent);
    }
    public void goToProduct(View view){
        Intent intent = new Intent(MainActivity.this, Product.class);
        startActivity(intent);
    }
    public void goToBusiness(View view){
        Intent intent = new Intent(MainActivity.this, Business.class);
        startActivity(intent);
    }


}
