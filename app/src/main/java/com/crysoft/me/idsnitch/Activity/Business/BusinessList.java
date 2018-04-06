package com.crysoft.me.idsnitch.Activity.Business;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.adapters.BusinessAdapter;
import com.crysoft.me.idsnitch.models.BusinessModel;

import java.util.ArrayList;

public class BusinessList extends AppCompatActivity {

    private ListView lvBusiness;
    private ArrayList<BusinessModel> businessList=new ArrayList<>();
    private BusinessModel business=new BusinessModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        businessList = getIntent().getParcelableArrayListExtra("businessList");

        BusinessAdapter businessAdapter = new BusinessAdapter(BusinessList.this,businessList);
        lvBusiness.setAdapter(businessAdapter);
        businessAdapter.notifyDataSetChanged();

        lvBusiness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(BusinessList.this,BusinessDetails.class);
                business = businessList.get(i);
                intent.putExtra("business",business);
                //based on item add info to intent
                startActivity(intent);
            }
        });
    }

}
