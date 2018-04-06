package com.crysoft.me.idsnitch.Activity.Professional;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crysoft.me.idsnitch.Activity.Business.Business;
import com.crysoft.me.idsnitch.MainActivity;
import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.ProfessionalModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Professional extends AppCompatActivity {
    private EditText etIdNumber;
    private TextView tvPersonWarning;
    private Button tvVerify;
    private LinearLayout llContent,llProgress;

    private VerifyIdentityTask verifyIdentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Get the ID Number Field
        etIdNumber = (EditText) findViewById(R.id.etIdentity);
        //Get the Layouts
        llContent = (LinearLayout) findViewById(R.id.llContent);
        llProgress = (LinearLayout) findViewById(R.id.llProgress);
        tvPersonWarning = (TextView) findViewById(R.id.personWarning);

        tvVerify = (Button) findViewById(R.id.tvVerify);

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyProfessional();
            }
        });

    }

    private void verifyProfessional() {
        String idNumber = etIdNumber.getText().toString();

        View focusView = null;
        Boolean cancel = false;

        //Check if the user entered anything
        if (TextUtils.isEmpty(idNumber)){
            focusView=etIdNumber;
            cancel = true;
        }

        if (cancel){
            //Nothing was entered,no need to proceed
            focusView.requestFocus();
        }else{
            verifyIdentity = new VerifyIdentityTask(idNumber);
            verifyIdentity.execute((Void) null);
        }
    }


    public void goToMain(View view){
        Intent intent = new Intent(Professional.this, MainActivity.class);
        startActivity(intent);
    }

    public class VerifyIdentityTask extends AsyncTask<Void, Void, Boolean>{
        private final String idNumber;

        InputStream inputStream = null;
        String result = "";
        String userId="";
        int errCode=1;
        ProfessionalModel professional = new ProfessionalModel();


        VerifyIdentityTask(String strIdNumber){
            idNumber = strIdNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llContent.setVisibility(View.GONE);
            llProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                URL hp=null;

                hp = new URL(getString(R.string.liveurl)+"/verify"+idNumber+"/professional");

                HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
                hpCon.connect();

                int responseCode = hpCon.getResponseCode();
                String errMessage = hpCon.getResponseMessage();

                if (responseCode == 200){
                    inputStream = hpCon.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line + '\n');
                    }
                    inputStream.close();
                    result = stringBuilder.toString();

                    JSONObject person = new JSONObject(result);

                    professional.setFirstName(person.getString("firstName"));
                    professional.setMiddleName(person.getString("middleName"));
                    professional.setLastName(person.getString("lastName"));
                    professional.setIdNumber(person.getString("idNumber"));
                    professional.setImageName(person.getString("imageName"));
                    professional.setUserId(person.getString("id"));

                    errCode = 0;
                }else {
                    errCode = 1;
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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (errCode == 0){
                Intent i = new Intent(Professional.this,ProfessionalDetails.class);
                //Push the Parceable Model through the intent
                i.putExtra("professional", professional);

                startActivity(i);
            }else {
                llProgress.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                tvPersonWarning.setVisibility(View.VISIBLE);
            }
        }
    }
}
