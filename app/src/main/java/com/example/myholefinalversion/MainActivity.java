package com.example.myholefinalversion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_btn;
    Button draw_btn;
    CustomAdapter adapter;
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    SQLiteDB myDB;
    ArrayList<String> hole_id, hole_location, hole_city, hole_country, hole_address, hole_latitude, hole_longitude;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                if(tx > 2.0f || tx < -2.0f){
                    Intent intent = new Intent(MainActivity.this, Add_activity.class);
                    startActivity(intent);
                }
                if(ty > 2.0f || ty < -2.0f){
                    Intent intent = new Intent(MainActivity.this, Add_activity.class);
                    startActivity(intent);
                }
            }
        });
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rz > 2.0f || rz < -2.0f){
                    Intent intent = new Intent(MainActivity.this, Add_activity.class);
                    startActivity(intent);
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.floatingActionButton);
        add_btn.setClickable(false);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_activity.class);
                startActivity(intent);
            }
        });

        myDB = new SQLiteDB(MainActivity.this);
        hole_location = new ArrayList<>();
        hole_city = new ArrayList<>();
        hole_country = new ArrayList<>();
        hole_address = new ArrayList<>();
        hole_latitude = new ArrayList<>();
        hole_longitude = new ArrayList<>();
        hole_id = new ArrayList<>();

        displayData();
        adapter = new CustomAdapter(MainActivity.this, this,  hole_id, hole_location, hole_city, hole_country, hole_address, hole_latitude, hole_longitude);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    void displayData(){
        Cursor cursor = myDB.readData();
        if(cursor == null){
            return;
        }
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                hole_id.add(cursor.getString(0));
                hole_location.add(cursor.getString(1));
                hole_city.add(cursor.getString(2));
                hole_country.add(cursor.getString(3));
                hole_address.add(cursor.getString(4));
                hole_latitude.add(cursor.getString(5));
                hole_longitude.add(cursor.getString(6));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }
}