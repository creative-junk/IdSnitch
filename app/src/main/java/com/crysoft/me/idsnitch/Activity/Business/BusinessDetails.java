package com.crysoft.me.idsnitch.Activity.Business;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.crysoft.me.idsnitch.Activity.Professional.ProfessionalDetails;
import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.adapters.CertificationAdapter;
import com.crysoft.me.idsnitch.adapters.EducationAdapter;
import com.crysoft.me.idsnitch.adapters.LicenseAdapter;
import com.crysoft.me.idsnitch.models.BusinessModel;
import com.crysoft.me.idsnitch.models.CertificationModel;
import com.crysoft.me.idsnitch.models.EducationModel;
import com.crysoft.me.idsnitch.models.LicenseModel;
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

public class BusinessDetails extends AppCompatActivity {
    private BusinessModel business;
    private ArrayList<LicenseModel> licenseList;
    private TextView tvBusinessName;
    private TextView tvBusinessNumber;
    private TextView tvCountry;
    private ListView lvLicense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvBusinessName = (TextView) findViewById(R.id.tvBusinessName);
        tvCountry = (TextView) findViewById(R.id.tvCountry);

        lvLicense = (ListView) findViewById(R.id.lvLicense);

        business = getIntent().getExtras().getParcelable("business");

        tvBusinessName.setText(business.getBusinessName());
        tvCountry.setText(business.getCountry());

        licenseList = new ArrayList<>();
        new BusinessLicenses(business.getId()).execute();

    }

    private class BusinessLicenses extends AsyncTask<Void,Void,Void> {
        private final String businessId;
        InputStream inputStream=null;
        String result = "";

        BusinessLicenses(String id){
            businessId=id;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL hp=null;
                licenseList.clear();

                hp = new URL(getString(R.string.liveurl)+"/api/view/"+businessId+"/business");

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
                    JSONArray licenses = profileObject.getJSONArray("license");

                    for (int i=0;i < licenses.length();i++){
                        JSONObject license = licenses.getJSONObject(i);
                        LicenseModel licenseDetails = new LicenseModel();
                        licenseDetails.setLicenseName(license.getString("licenseName"));
                        licenseDetails.setLicensePeriod(license.getString("licensePeriod"));
                        licenseDetails.setIssuedBy(license.getString("issuedBy"));
                        licenseList.add(licenseDetails);
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

            LicenseAdapter licenseAdapter = new LicenseAdapter(BusinessDetails.this,licenseList);
            lvLicense.setAdapter(licenseAdapter);
            licenseAdapter.notifyDataSetChanged();

        }
    }

}
