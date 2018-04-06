package com.crysoft.me.idsnitch.Activity.Business;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crysoft.me.idsnitch.Activity.Product.Product;
import com.crysoft.me.idsnitch.Activity.Professional.Professional;
import com.crysoft.me.idsnitch.Activity.Professional.ProfessionalDetails;
import com.crysoft.me.idsnitch.MainActivity;
import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.adapters.BusinessAdapter;
import com.crysoft.me.idsnitch.adapters.CertificationAdapter;
import com.crysoft.me.idsnitch.adapters.EducationAdapter;
import com.crysoft.me.idsnitch.models.BusinessModel;
import com.crysoft.me.idsnitch.models.CertificationModel;
import com.crysoft.me.idsnitch.models.EducationModel;

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

public class Business extends AppCompatActivity {
    private LinearLayout llContent,llProgress;
    private EditText etBusinessName;
    private Button tvVerify;
    private TextView tvBusinessWarning;
    private ListView lvBusiness;

    private VerifyBusinessTask verifyBusiness;
    private ArrayList<BusinessModel> businessList;
    private BusinessModel business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        business = new BusinessModel();
        businessList = new ArrayList<>();

        //Get the Layouts
        llContent = (LinearLayout) findViewById(R.id.llContent);
        llProgress = (LinearLayout) findViewById(R.id.llProgress);
        lvBusiness = (ListView) findViewById(R.id.lvBusiness);


        etBusinessName = (EditText) findViewById(R.id.etBusinessName);

        tvBusinessWarning = (TextView) findViewById(R.id.tvBusinessWarning);

        tvVerify = (Button) findViewById(R.id.tvVerify);

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyBusiness();
            }
        });


    }
    public void goToMain(View view){
        Intent intent = new Intent(Business.this, MainActivity.class);
        startActivity(intent);
    }
    private void verifyBusiness() {
        String businessName = etBusinessName.getText().toString();

        View focusView = null;
        Boolean cancel = false;

        //Check if the user entered anything
        if (TextUtils.isEmpty(businessName)){
            focusView=etBusinessName;
            cancel = true;
        }
        if (cancel){
            //Nothing was entered,no need to proceed
            focusView.requestFocus();
        }else{
            verifyBusiness = new VerifyBusinessTask(businessName);
            verifyBusiness.execute((Void) null);
        }
    }


    private class VerifyBusinessTask extends AsyncTask<Void,Void,Void> {
        private final String businessName;
        InputStream inputStream=null;
        String result = "";
        int errCode=1;

        VerifyBusinessTask(String strBusinessName){
            businessName=strBusinessName;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL hp=null;
                businessList.clear();

                hp = new URL(getString(R.string.liveurl)+"/api/verify/"+businessName+"/business");

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
                    JSONArray businesses = profileObject.getJSONArray("business");

                    for (int i=0;i < businesses.length();i++){
                        JSONObject business = businesses.getJSONObject(i);
                        BusinessModel businessDetails = new BusinessModel();
                        businessDetails.setBusinessName(business.getString("businessName"));
                        businessDetails.setCountry(business.getString("country"));
                        businessDetails.setId(business.getString("id"));
                        businessList.add(businessDetails);
                    }
                    errCode = 0;

                }else {
                    errCode =1;
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

            if (errCode == 0){
                Intent i = new Intent(Business.this,ProfessionalDetails.class);
                //Push the Parceable Model through the intent
                i.putParcelableArrayListExtra("businessList", businessList);

                startActivity(i);
            }else {
                llProgress.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                tvBusinessWarning.setVisibility(View.VISIBLE);
            }

        }
    }


}
