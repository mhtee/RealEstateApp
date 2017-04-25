package com.teamoutis.realestateapp.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamoutis.realestateapp.Adapters.PropertyAdapter;
import com.teamoutis.realestateapp.Models.Property;
import com.teamoutis.realestateapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {

    private PropertyAdapter propertyAdapter;
    ArrayList<Property> properties;
    Intent receiver;
    String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView) toolbar.findViewById(R.id.activityTitle);
        toolbarText.setText("Properties");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        receiver = getIntent();
        json = receiver.getStringExtra("jsonObject");
        properties = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String address = jsonObject.getString("address");
                String zipString = jsonObject.getString("Zip_Code");
                String bedString = jsonObject.getString("bedroom");
                String bathString = jsonObject.getString("bathroom");
                String priceString = jsonObject.getString("price");
                String link = jsonObject.getString("link");

                zipString = zipString.replaceAll("[^\\d.]", "");
                bedString = bedString.replaceAll("[^\\d.]", "");
                bathString = bathString.replaceAll("[^\\d.]", "");
                priceString = priceString.replaceAll("[^\\d.]", "");
                address = address.replaceAll("\\[\"(.*?)\"\\]", "$1");
                link = link.replaceAll("\\[\"(.*?)\"\\]", "$1");
                link = link.replaceAll("\\\\/", "/");

                int zipCode;
                if (!zipString.isEmpty()){
                    zipCode = Integer.parseInt(zipString);
                } else {
                    zipCode = -1;
                }
                int bedroom;
                if (!bedString.isEmpty()){
                    bedroom= Integer.parseInt(bedString);
                } else {
                    bedroom = -1;
                }
                int bathroom;
                if (!bathString.isEmpty()){
                    bathroom = Integer.parseInt(bathString);
                } else {
                    bathroom = -1;
                }

                double price;
                Log.d("PRICE: ", priceString);
                if (!priceString.isEmpty() && priceString != null){
                    price = Double.parseDouble(priceString);
                }
                else {
                    price = -1;
                }


                properties.add(new Property(address, bedroom, bathroom, zipCode, price, link));



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        properties.add(new Property("123 Main St, Houston, TX, 77002", 2, 2, 77002, 200000));
        properties.add(new Property("1122 Bellaire St, Houston, TX, 77074", 2, 1, 77074, 250000));
        properties.add(new Property("9972 Beechnut St, Houston, TX, 77036", 3, 3, 77036, 300000));
        properties.add(new Property("123 Fannin St, Houston, TX, 77002", 5, 4, 77002, 450000));
        properties.add(new Property("1159 Kirby St, Houston, TX, 77099", 6, 5, 77099, 500000));
        properties.add(new Property("123 Williams Trace St, Houston, TX, 77498", 5, 4, 77498, 750000));
        properties.add(new Property("1235 Kirby St, Houston, TX, 77099", 6, 5, 77099, 500000));
        */

        propertyAdapter = new PropertyAdapter(properties);

        final RecyclerView propertyRecyclerView =
                (RecyclerView) findViewById(R.id.propertyRecyclerView);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(getBaseContext());
        propertyRecyclerView.setLayoutManager(llm);
        propertyRecyclerView.setAdapter(propertyAdapter);
    }



    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
