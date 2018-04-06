package com.crysoft.me.idsnitch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.BusinessModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 4/6/2018.
 */

public class BusinessAdapter extends BaseAdapter {
    private ArrayList<BusinessModel> businesses;
    private LayoutInflater mInflater;
    private Context myContext;

    public BusinessAdapter(Context context, ArrayList<BusinessModel> results){
        businesses = results;
        mInflater = LayoutInflater.from(context);
        myContext = context;
    }

    @Override
    public int getCount() {
        return businesses.size();
    }

    @Override
    public Object getItem(int i) {
        return businesses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.single_business,null);

        TextView businessName = (TextView) convertView.findViewById(R.id.tvBusinessName);

        businessName.setText(businesses.get(i).getBusinessName());
        return convertView;
    }
}
