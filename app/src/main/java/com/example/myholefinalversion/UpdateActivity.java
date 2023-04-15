package com.example.myholefinalversion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, city_input, country_input, address_input, latitude_input, longitude_input;
    Button updateButton, deleteButton;
    String id, name, city, country, address, latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.Location_Name2);
        city_input = findViewById(R.id.city_text2);
        country_input = findViewById(R.id.country_text2);
        address_input = findViewById(R.id.address_text2);
        latitude_input = findViewById(R.id.latitude_text2);
        longitude_input = findViewById(R.id.longitude_text2);
        getAndSetIntentData();
        updateButton = findViewById(R.id.update_btn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDB myDB = new SQLiteDB(UpdateActivity.this);
                myDB.updateData(id, name, city, country, address, latitude, longitude);
            }
        });
        deleteButton = findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("Id") && getIntent().hasExtra("Location") && getIntent().hasExtra("City") && getIntent().hasExtra("Country")
                && getIntent().hasExtra("Address") && getIntent().hasExtra("Latitude") && getIntent().hasExtra("Longitude")){
            id = getIntent().getStringExtra("Id");
            name = getIntent().getStringExtra("Location");
            city = getIntent().getStringExtra("City");
            country = getIntent().getStringExtra("Country");
            address = getIntent().getStringExtra("Address");
            latitude = getIntent().getStringExtra("Latitude");
            longitude = getIntent().getStringExtra("Longitude");

            name_input.setText(name);
            city_input.setText(city);
            country_input.setText(country);
            address_input.setText(address);
            latitude_input.setText(latitude);
            longitude_input.setText(longitude);
            //Log.d("stev", name+" "+ coordinates);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + name + "?");
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDB myDB = new SQLiteDB(UpdateActivity.this);
                myDB.deleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}