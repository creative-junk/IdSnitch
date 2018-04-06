package com.crysoft.me.idsnitch.Activity.Professional;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.adapters.CertificationAdapter;
import com.crysoft.me.idsnitch.adapters.EducationAdapter;
import com.crysoft.me.idsnitch.models.CertificationModel;
import com.crysoft.me.idsnitch.models.EducationModel;
import com.crysoft.me.idsnitch.models.ProfessionalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProfessionalDetails extends AppCompatActivity {
    private ProfessionalModel professional;
    private ArrayList<CertificationModel> certificationList;
    private ArrayList<EducationModel> educationList;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvMiddleName;
    private TextView tvIdNumber;
    private TextView tvCountry;
    private ListView lvCertification;
    private ListView lvEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvMiddleName = (TextView) findViewById(R.id.tvMiddleName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvIdNumber = (TextView) findViewById(R.id.tvIdNumber);
        tvCountry = (TextView) findViewById(R.id.tvCountry);


        professional = getIntent().getExtras().getParcelable("professional");

        tvFirstName.setText(professional.getFirstName());
        tvMiddleName.setText(professional.getMiddleName());
        tvLastName.setText(professional.getLastName());
        tvIdNumber.setText(professional.getIdNumber());
        tvCountry.setText(professional.getCountry());

        certificationList = new ArrayList<>();
        educationList = new ArrayList<>();

        lvEducation = (ListView) findViewById(R.id.lvEducation);
        lvCertification = (ListView) findViewById(R.id.lvCertifications);

        new UserProfile(professional.getUserId()).execute();
    }
    private class UserProfile extends AsyncTask<Void,Void,Void>{
        private final String userId;
        InputStream inputStream=null;
        String result = "";

        UserProfile(String id){
            userId=id;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL hp=null;
                educationList.clear();
                certificationList.clear();

                hp = new URL(getString(R.string.liveurl)+"/api/profile/"+userId+"/view");

                HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
                hpCon.connect();

                int responseCode = hpCon.getResponseCode();
                String errMessage = hpCon.getResponseMessage();

                if (responseCode == 200){
                    inputStream = hpCon.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line = null;
                    while((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line + '\n');
                    }
                    inputStream.close();
                    result = stringBuilder.toString();

                    JSONObject profileObject = new JSONObject(result);
                    JSONArray certificates = profileObject.getJSONArray("certification");
                    JSONArray educations = profileObject.getJSONArray("education");

                    int certLength = certificates.length();
                    int eduLength = educations.length();

                    for (int i=0;i < certificates.length();i++){
                        JSONObject cert = certificates.getJSONObject(i);
                        CertificationModel certificateDetails = new CertificationModel();
                        certificateDetails.setOrganization(cert.getString("organization"));
                        certificateDetails.setCertification(cert.getString("certification"));
                        certificateDetails.setPeriod(cert.getString("period"));
                        certificateDetails.setQualification(cert.getString("qualification"));
                        certificateDetails.setCategory(cert.getString("category"));
                        certificationList.add(certificateDetails);
                    }

                    for (int i=0;i<educations.length();i++){
                        JSONObject edu = educations.getJSONObject(i);
                        EducationModel educationDetails = new EducationModel();
                        educationDetails.setInstitution(edu.getString("institution"));
                        educationDetails.setGraduationYear(edu.getString("graduationYear"));
                        educationDetails.setQualification(edu.getString("qualification"));
                        educationList.add(educationDetails);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            EducationAdapter educationAdapter = new EducationAdapter(ProfessionalDetails.this,educationList);
            lvEducation.setAdapter(educationAdapter);
            educationAdapter.notifyDataSetChanged();

            CertificationAdapter certificationAdapter = new CertificationAdapter(ProfessionalDetails.this,certificationList);
            lvCertification.setAdapter(certificationAdapter);
            certificationAdapter.notifyDataSetChanged();

        }
    }
}
