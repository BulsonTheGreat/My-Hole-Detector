package com.example.myholefinalversion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList hole_id, hole_name, hole_city, hole_country, hole_address, hole_latitude, hole_longitude;
    CustomAdapter(Activity activity, Context context, ArrayList hole_id, ArrayList hole_name, ArrayList hole_city, ArrayList hole_country,
                ArrayList hole_address,ArrayList hole_latitude, ArrayList hole_longitude){
        this.activity = activity;
        this.context = context;
        this.hole_id = hole_id;
        this.hole_name = hole_name;
        this.hole_city = hole_city;
        this.hole_country = hole_country;
        this.hole_address = hole_address;
        this.hole_latitude = hole_latitude;
        this.hole_longitude = hole_longitude;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.holeId_text.setText(String.valueOf(hole_id.get(position)));
        holder.holeName_text.setText(String.valueOf(hole_name.get(position)));
        holder.holeCity_text.setText(String.valueOf(hole_city.get(position)));
        holder.holeCountry_text.setText(String.valueOf(hole_country.get(position)));
        holder.holeAddress_text.setText(String.valueOf(hole_address.get(position)));
        holder.holeLongitude_text.setText(String.valueOf(hole_longitude.get(position)));
        holder.holeLatitude_text.setText(String.valueOf(hole_latitude.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("Id", String.valueOf(hole_id.get(position)));
                intent.putExtra("Location", String.valueOf(hole_name.get(position)));
                intent.putExtra("City", String.valueOf(hole_city.get(position)));
                intent.putExtra("Country", String.valueOf(hole_country.get(position)));
                intent.putExtra("Address", String.valueOf(hole_address.get(position)));
                intent.putExtra("Latitude", String.valueOf(hole_latitude.get(position)));
                intent.putExtra("Longitude", String.valueOf(hole_longitude.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hole_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView holeId_text, holeName_text, holeCity_text, holeCountry_text, holeAddress_text, holeLatitude_text, holeLongitude_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            holeId_text = itemView.findViewById(R.id.hole_id_text);
            holeName_text = itemView.findViewById(R.id.hole_name_text);
            holeCity_text = itemView.findViewById(R.id.hole_city_text);
            holeCountry_text = itemView.findViewById(R.id.hole_country);
            holeAddress_text = itemView.findViewById(R.id.hole_address);
            holeLatitude_text = itemView.findViewById(R.id.hole_latitude);
            holeLongitude_text = itemView.findViewById(R.id.hole_longitude);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
