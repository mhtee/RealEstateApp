package com.teamoutis.realestateapp.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.teamoutis.realestateapp.Models.Property;
import com.teamoutis.realestateapp.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Muhammad on 4/7/2017.
 */

public class PropertyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Property> properties;

    public PropertyAdapter(ArrayList<Property> properties){
        this.properties = properties;
    }

    @Override
    public PViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_card, parent, false);

        return new PViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Property property = properties.get(position);
        final PViewHolder holder = (PViewHolder) viewHolder;
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        holder.address.setText(property.getAddress());
        holder.bedroom.setText("Bedrooms: " + String.valueOf(property.getBedroom()));
        holder.bathroom.setText("Bathrooms: " + String.valueOf(property.getBathroom()));
        holder.zipcode.setText("Zip Code: " + String.valueOf(property.getZipCode()));
        holder.price.setText(numberFormat.format(property.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "You're trying to select a Property",
                        Toast.LENGTH_LONG).show();

                String link = "https://trulia.com" + property.getLink();
                Log.d("LINK: ", link);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                holder.itemView.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    class PViewHolder extends RecyclerView.ViewHolder {
        public TextView address;
        public TextView bedroom;
        public TextView bathroom;
        public TextView distance;
        public TextView price;
        public TextView zipcode;

        public PViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.AddressTextView);
            bedroom = (TextView) itemView.findViewById(R.id.BedroomView);
            bathroom = (TextView) itemView.findViewById(R.id.BathroomView);
            price = (TextView) itemView.findViewById(R.id.PriceView);
            zipcode= (TextView) itemView.findViewById(R.id.ZipCodeView);
        }
    }
}
