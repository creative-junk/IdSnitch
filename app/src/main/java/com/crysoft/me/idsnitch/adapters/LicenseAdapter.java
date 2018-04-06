package com.crysoft.me.idsnitch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.EducationModel;
import com.crysoft.me.idsnitch.models.LicenseModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 4/6/2018.
 */

public class LicenseAdapter extends BaseAdapter {
    private ArrayList<LicenseModel> licenses;
    private LayoutInflater mInflater;
    private Context myContext;

    public LicenseAdapter(Context context, ArrayList<LicenseModel> results){
        licenses = results;
        mInflater = LayoutInflater.from(context);
        myContext = context;
    }
    @Override
    public int getCount() {
        return licenses.size();
    }

    @Override
    public Object getItem(int i) {
        return licenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.single_license,null);

        TextView licenseName = (TextView) convertView.findViewById(R.id.tvLicenseName);
        TextView licensePeriod = (TextView) convertView.findViewById(R.id.tvPeriod);
        TextView issuedBy = (TextView) convertView.findViewById(R.id.tvIssuedBy);

        licenseName.setText(licenses.get(i).getLicenseName());
        licensePeriod.setText(licenses.get(i).getLicensePeriod());
        issuedBy.setText(licenses.get(i).getIssuedBy());

        return convertView;
    }
}
