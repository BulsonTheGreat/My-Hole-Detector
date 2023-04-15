package com.example.myholefinalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class Add_activity extends AppCompatActivity {

    EditText location_input;
    TextView address, city, country, latitude, longitude;
    Button add_btn, location_btn;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        address = findViewById(R.id.address_text);
        city = findViewById(R.id.city_text);
        country = findViewById(R.id.country_text);
        latitude = findViewById(R.id.latitude_text);
        longitude = findViewById(R.id.longitude_text);
        location_input = findViewById(R.id.Location_Name);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location_btn = findViewById(R.id.btn_locate);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        add_btn = findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDB myDB = new SQLiteDB(Add_activity.this);
                myDB.addHole(location_input.getText().toString().trim(), city.getText().toString().trim(), country.getText().toString().trim(),
                    address.getText().toString().trim(), latitude.getText().toString().trim(), longitude.getText().toString().trim());
            }
        });
    }
    private void getLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        Geocoder geocoder = new Geocoder(Add_activity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try{
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            latitude.setText("Latitude: "+addresses.get(0).getLatitude());
                            longitude.setText("Latitude: "+addresses.get(0).getLongitude());
                            address.setText("Address: "+addresses.get(0).getAddressLine(0));
                            country.setText("Country: "+addresses.get(0).getCountryName());
                            city.setText("City: "+addresses.get(0).getLocality());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}