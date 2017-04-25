package com.teamoutis.realestateapp.Controllers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.teamoutis.realestateapp.Adapters.PropertyAdapter;
import com.teamoutis.realestateapp.Models.Property;
import com.teamoutis.realestateapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {

    private PropertyAdapter propertyAdapter;
    ArrayList<Property> properties;
    Intent receiver;
    String json;
    double priceMin = 0;
    double priceMax = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting up activity and toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView) toolbar.findViewById(R.id.activityTitle);
        toolbarText.setText("Properties");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Receiving data from previous activity
        receiver = getIntent();
        json = receiver.getStringExtra("jsonObject");
        priceMin = receiver.getDoubleExtra("priceMin", 0);
        priceMax = receiver.getDoubleExtra("priceMax", 0);
        properties = new ArrayList<>();

        // Converting JSON array into an arraylist of properties
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String priceString = jsonObject.getString("price");

                priceString = priceString.replaceAll("[^\\d.]", "");
                double price;
                if (!priceString.isEmpty() && priceString != null){
                    price = Double.parseDouble(priceString);
                }
                else {
                    price = -1;
                }

                if (priceMin != 0 && price < priceMin){
                    continue;
                } else if(priceMax != 0 && price > priceMax){
                    continue;
                }


                String address = jsonObject.getString("address");
                String zipString = jsonObject.getString("Zip_Code");
                String bedString = jsonObject.getString("bedroom");
                String bathString = jsonObject.getString("bathroom");
                String link = jsonObject.getString("link");

                zipString = zipString.replaceAll("[^\\d.]", "");
                bedString = bedString.replaceAll("[^\\d.]", "");
                bathString = bathString.replaceAll("[^\\d.]", "");
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




                properties.add(new Property(address, bedroom, bathroom, zipCode, price, link));



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Property Adapter
        propertyAdapter = new PropertyAdapter(properties);

        final RecyclerView propertyRecyclerView =
                (RecyclerView) findViewById(R.id.propertyRecyclerView);

        // Attaching adapter to recycler view
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
