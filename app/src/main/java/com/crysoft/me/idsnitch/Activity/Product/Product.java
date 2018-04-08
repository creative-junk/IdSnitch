package com.crysoft.me.idsnitch.Activity.Product;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crysoft.me.idsnitch.Activity.Professional.Professional;
import com.crysoft.me.idsnitch.Activity.Professional.ProfessionalDetails;
import com.crysoft.me.idsnitch.MainActivity;
import com.crysoft.me.idsnitch.R;
import com.crysoft.me.idsnitch.models.BrandModel;
import com.crysoft.me.idsnitch.models.ProductModel;
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

public class Product extends AppCompatActivity {
    private LinearLayout llContent,llProgress;
    private EditText etSerialNumber;
    private Spinner brandsSpinner;

    private ArrayList<BrandModel> brandList;
    ArrayList<String> spinnerArray =  new ArrayList<String>();
    private Button tvVerify;
    private TextView tvProductWarning;

    private VerifyProductTask verifyProduct;
    private GetBrands getBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        brandList = new ArrayList<>();
        //Get the Layouts
        llContent = (LinearLayout) findViewById(R.id.llContent);
        llProgress = (LinearLayout) findViewById(R.id.llProgress);

        etSerialNumber = (EditText) findViewById(R.id.etSerialNumber);
        brandsSpinner = (Spinner) findViewById(R.id.brands_spinner);
        tvProductWarning = (TextView) findViewById(R.id.tvProductWarning);

        getBrands = new GetBrands();
        getBrands.execute();

        tvVerify = (Button) findViewById(R.id.tvProductVerify);

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyProduct();
            }
        });


    }
    public void goToMain(View view){
        Intent intent = new Intent(Product.this, MainActivity.class);
        startActivity(intent);
    }

    private void verifyProduct() {
        String idNumber = etSerialNumber.getText().toString();

        String selectedBrand = brandsSpinner.getSelectedItem().toString();

        View focusView = null;
        Boolean cancel = false;

        //Check if the user entered anything
        if (TextUtils.isEmpty(idNumber)){
            focusView=etSerialNumber;
            cancel = true;
        }
        if (TextUtils.isEmpty(selectedBrand) || selectedBrand.equals("Choose Brand")){
            focusView=brandsSpinner;
            cancel = true;
        }

        if (cancel){
            //Nothing was entered,no need to proceed
            focusView.requestFocus();
        }else{
            verifyProduct = new VerifyProductTask(idNumber,selectedBrand);
            verifyProduct.execute((Void) null);
        }
    }


    public class VerifyProductTask extends AsyncTask<Void, Void, Boolean> {
        private final String serialNumber,brand;

        InputStream inputStream = null;
        String result = "";
        String productId="";
        int errCode=1;
        ProductModel product = new ProductModel();


        VerifyProductTask(String strSerialNumber,String strBrand){
            serialNumber=strSerialNumber;
            brand = strBrand;
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

                hp = new URL(getString(R.string.liveurl)+"/verify/"+brand+":"+serialNumber+"/product");

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

                    JSONObject merchandise = new JSONObject(result);

                    product.setSerialNumber(merchandise.getString("serialNumber"));
                    product.setDescription(merchandise.getString("description"));
                    product.setDistributor(merchandise.getString("distributor"));
                    product.setBrand(merchandise.getString("brand"));

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
                Intent i = new Intent(Product.this,ProductDetails.class);
                //Push the Parceable Model through the intent
                i.putExtra("product", product);

                startActivity(i);
            }else {
                llProgress.setVisibility(View.GONE);
                llContent.setVisibility(View.VISIBLE);
                tvProductWarning.setVisibility(View.VISIBLE);
            }
        }
    }
    private class GetBrands extends AsyncTask<Void,Void,Void> {
        InputStream inputStream=null;
        String result = "";
        int errCode=1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL hp=null;
                brandList.clear();

                hp = new URL(getString(R.string.liveurl)+"/brands");
                Log.i("URL",hp.toString());
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
                    JSONArray brands = profileObject.getJSONArray("brands");
                    spinnerArray.add("Choose Brand");
                    for (int i=0;i < brands.length();i++){
                        JSONObject brand = brands.getJSONObject(i);
                        BrandModel brandDetails = new BrandModel();
                        brandDetails.setName(brand.getString("name"));
                        spinnerArray.add(brand.getString("name"));
                        brandList.add(brandDetails);
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    Product.this, android.R.layout.simple_spinner_item, spinnerArray);
            Log.i("Spinner Items",brandList.toString());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brandsSpinner.setAdapter(adapter);
        }
    }

}
