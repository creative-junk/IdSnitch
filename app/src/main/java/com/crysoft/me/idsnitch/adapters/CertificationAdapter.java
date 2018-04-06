package com.crysoft.me.idsnitch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.BusinessModel;
import com.crysoft.me.idsnitch.models.CertificationModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 4/6/2018.
 */

public class CertificationAdapter extends BaseAdapter {
    private ArrayList<CertificationModel> certifications;
    private LayoutInflater mInflater;
    private Context myContext;

    public CertificationAdapter(Context context, ArrayList<CertificationModel> results){
        certifications = results;
        mInflater = LayoutInflater.from(context);
        myContext = context;
    }
    @Override
    public int getCount() {
        return certifications.size();
    }

    @Override
    public Object getItem(int i) {
        return certifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.single_certification,null);

        TextView organization= (TextView) convertView.findViewById(R.id.tvIssuedBy);
        TextView certification= (TextView) convertView.findViewById(R.id.tvCertification);
        TextView period = (TextView) convertView.findViewById(R.id.tvPeriod);
        TextView qualification = (TextView) convertView.findViewById(R.id.tvQualification);
        TextView category = (TextView) convertView.findViewById(R.id.tvCategory);

        organization.setText(certifications.get(i).getOrganization());
        certification.setText(certifications.get(i).getCertification());
        period.setText(certifications.get(i).getPeriod());
        qualification.setText(certifications.get(i).getQualification());
        category.setText(certifications.get(i).getCategory());

        return convertView;
    }
}
