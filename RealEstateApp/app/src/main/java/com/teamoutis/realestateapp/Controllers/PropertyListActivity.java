package com.teamoutis.realestateapp.Controllers;

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

import com.teamoutis.realestateapp.Adapters.PropertyAdapter;
import com.teamoutis.realestateapp.Models.Property;
import com.teamoutis.realestateapp.R;

import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {

    private PropertyAdapter propertyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = (TextView) toolbar.findViewById(R.id.activityTitle);
        toolbarText.setText("Welcome!");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(new Property("123 Main St, Houston, TX, 77002", 2, 2, 6.87, 200000));
        properties.add(new Property("1122 Bellaire St, Houston, TX, 77074", 2, 1, 3.27, 250000));
        properties.add(new Property("9972 Beechnut St, Houston, TX, 77036", 3, 3, 2, 300000));
        properties.add(new Property("123 Fannin St, Houston, TX, 77002", 5, 4, 7.7, 450000));
        properties.add(new Property("1159 Kirby St, Houston, TX, 77099", 6, 5, 3.5, 500000));
        properties.add(new Property("123 Williams Trace St, Houston, TX, 77498", 5, 4, 10.5, 750000));
        properties.add(new Property("1235 Kirby St, Houston, TX, 77099", 6, 5, 3.5, 500000));

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
