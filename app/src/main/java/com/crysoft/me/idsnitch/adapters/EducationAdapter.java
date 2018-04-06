package com.crysoft.me.idsnitch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.CertificationModel;
import com.crysoft.me.idsnitch.models.EducationModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 4/6/2018.
 */

public class EducationAdapter extends BaseAdapter {
    private ArrayList<EducationModel> educations;
    private LayoutInflater mInflater;
    private Context myContext;

    public EducationAdapter(Context context, ArrayList<EducationModel> results){
        educations = results;
        mInflater = LayoutInflater.from(context);
        myContext = context;
    }
    @Override
    public int getCount() {
        return educations.size();
    }

    @Override
    public Object getItem(int i) {
        return educations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.single_education,null);
        TextView graduationYear = (TextView) convertView.findViewById(R.id.tvGraduationYear);
        TextView qualification = (TextView) convertView.findViewById(R.id.tvQualification);
        TextView institution = (TextView) convertView.findViewById(R.id.tvIssuedBy);

        graduationYear.setText(educations.get(i).getGraduationYear());
        qualification.setText(educations.get(i).getQualification());
        institution.setText(educations.get(i).getInstitution());
        return convertView;
    }
}
