package com.teamoutis.realestateapp.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamoutis.realestateapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText CityBox, ZipBox, BedroomBox, BathroomBox, PriceMinBox, PriceMaxBox;
    String city = "";
    int zipCode = -1;
    int bedroom = -1;
    int bathroom = -1;
    double priceMin = 0;
    double priceMax = 0;

    public void searchFunction (View view) {

        CityBox = (EditText) findViewById(R.id.CityBox);
        city = CityBox.getText().toString().toLowerCase();
        ZipBox = (EditText) findViewById(R.id.ZipBox);
        String temp = ZipBox.getText().toString();
        if (isNumeric(temp) && (temp.length() == 5 || temp.length() == 0)){
            if (temp.isEmpty()){
                zipCode = -1;
            } else {
                zipCode = Integer.parseInt(temp);
            }
        } else {
            Toast.makeText(getBaseContext(), "Wrong format: zipcode", Toast.LENGTH_SHORT).show();
            return;
        }
        BedroomBox = (EditText) findViewById(R.id.BedroomBox);
        temp = BedroomBox.getText().toString();
        if (isNumeric(temp)){
            if (temp.isEmpty()){
                bedroom = -1;
            } else {
                bedroom = Integer.parseInt(temp);
            }
        } else {
            Toast.makeText(getBaseContext(), "Wrong format: bedroom", Toast.LENGTH_SHORT).show();
            return;
        }

        BathroomBox = (EditText) findViewById(R.id.BathroomBox);
        temp = BathroomBox.getText().toString();
        if (isNumeric(temp)){
            if (temp.isEmpty()){
                bathroom = -1;
            } else {
                bathroom = Integer.parseInt(temp);
            }
        } else {
            Toast.makeText(getBaseContext(), "Wrong format: bathroom", Toast.LENGTH_SHORT).show();
            return;
        }
        PriceMinBox = (EditText) findViewById(R.id.priceMinBox);
        temp = PriceMinBox.getText().toString();
        if (!temp.isEmpty() && temp != null){
            priceMin = Double.parseDouble(temp);
        }
        PriceMaxBox = (EditText) findViewById(R.id.priceMaxBox);
        temp = PriceMaxBox.getText().toString();
        if (!temp.isEmpty() && temp != null){
            priceMax = Double.parseDouble(temp);
        }

        city = city.replaceAll(" ", "%20");


        Toast.makeText(getBaseContext(), "Retrieving...", Toast.LENGTH_LONG).show();
        GetData data = new GetData();
        data.execute("http://34.208.154.237/request/" + city + "/" + Integer.toString(zipCode) +
                "/" + Integer.toString(bedroom) + "/" + Integer.toString(bathroom));

    }

    public boolean isNumeric(String string){
        if (string.isEmpty()){
            return true;
        }
        try
        {
            int x = Integer.parseInt(string);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView) toolbar.findViewById(R.id.activityTitle);
        toolbarText.setText("Search");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView searchTextView = (TextView) findViewById(R.id.CityTextView);
        EditText searchBox = (EditText) findViewById(R.id.CityBox);
        Log.d("Debug", searchBox.getText().toString());

        //Button button = (Button) findViewById(R.id.button);
    }


    public class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {


            String result = "";
            URL url;
            HttpURLConnection httpURL = null;


            try {
                url = new URL(urls[0]);
                httpURL = (HttpURLConnection) url.openConnection();
                InputStream is = httpURL.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is);
                int data = isReader.read();

                while(data != -1){
                    char c = (char) data;
                    result += c;
                    data = isReader.read();

                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Connection Failed!";

        }

        @Override
        protected void onPostExecute(String output){
            super.onPostExecute(output);

            if (output.equals("Connection Failed!")){
                Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(getBaseContext(), PropertyListActivity.class);
                intent.putExtra("jsonObject", output);
                intent.putExtra("priceMin", priceMin);
                intent.putExtra("priceMax", priceMax);
                startActivity(intent);
            }

        }
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
