package com.example.pinmycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private int locationRequestCode = 1000;
    private double latitude = 0.0, longitude = 0.0;

    private EditText label;

    ArrayList<Pin> pins;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare all pins list view
        // TODO : Get all pins in this view
        recyclerView = findViewById(R.id.pinsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PinsAdapter adapter = new PinsAdapter(pins);
        recyclerView.setAdapter(adapter);

        // Use location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check permission to access location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        }

        // Get location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            // txtLocation.setText(String.format(Locale.FRANCE, "%s , %s", latitude, longitude));
                        }
                    }
                });

        createFile(getApplicationContext());
    }

    // On click on save button = create new Pin (from Pin class)
    public void save(View view) {
        Pin pin = new Pin();
        pin.label = this.label.getText().toString();
        pin.date = new Date();
        pin.latitude = this.latitude;
        pin.longitude = this.longitude;
        pins.add(pin);
        writeFile(getApplicationContext(), pin);
    };

    private String fileName = "pins.txt";

    public void createFile(Context context) {
        File file = new File(context.getCacheDir(), fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
                Toast.makeText(context, String.format("File %s has been created", fileName), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, String.format("File %s creation failed", fileName), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, String.format("File %s already exists", fileName), Toast.LENGTH_SHORT).show();
        }
    }

    public void writeFile(Context context, Object pin) {
        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(pin.toString());
            outputStreamWriter.close();
            Toast.makeText(context, String.format("Write to %s successful", fileName), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, String.format("Write to file %s failed", fileName), Toast.LENGTH_SHORT).show();
        }
    }
}