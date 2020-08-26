package com.example.pinmycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private int locationRequestCode = 1000;
    private double latitude = 0.0, longitude = 0.0;
    private EditText label;
    private Date date;
    private Button saveButton;
    private RecyclerView pinsList;

    private TextView txtLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare all pins list view
        // TODO : Get all pins in this view
        pinsList = (RecyclerView) findViewById(R.id.pinsList);

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
    }

    // On click on save button = create new Pin (from Pin class)
    public void save(View view) {
        Pin pin = new Pin();
        pin.label = this.label;
        pin.date = new Date();
        pin.latitude = this.latitude;
        pin.longitude = this.longitude;
    };
}