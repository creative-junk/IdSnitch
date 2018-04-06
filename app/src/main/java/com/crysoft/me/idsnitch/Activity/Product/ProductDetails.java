package com.crysoft.me.idsnitch.Activity.Product;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.ProductModel;

public class ProductDetails extends AppCompatActivity {
    private TextView tvSerialNumber;
    private TextView tvBrand;
    private TextView tvProductInfo;
    private TextView tvDistributorInfo;

    ProductModel product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvSerialNumber = (TextView) findViewById(R.id.tvSerialNumber);
        tvBrand = (TextView) findViewById(R.id.tvBrand);
        tvProductInfo = (TextView) findViewById(R.id.tvProductInfo);
        tvDistributorInfo = (TextView) findViewById(R.id.tvDistributorInfo);

        product = getIntent().getExtras().getParcelable("product");

        tvSerialNumber.setText(product.getSerialNumber());
        tvBrand.setText(product.getBrand());
        tvProductInfo.setText(product.getDescription());
        tvDistributorInfo.setText(product.getDistributor());

    }

}
