package com.example.kpak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResponderActivity extends AppCompatActivity {

    private TextView tvLocation;
    private TextView tvDetails;
    private Spinner spinnerStatus;
    private Button btnUpdateStatus;
    private Button btnViewDatabase;
    private Button btnViewCrime;
    private ListView routesListView;
    private SQLiteDatabase mDatabase;
    private ArrayList<String> routesList;
    private ArrayAdapter<String> routesAdapter;
    String location = "Azizabad";
    String details = "Immediate Assistance Required";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder);

        tvLocation = findViewById(R.id.tvLocation);
        tvDetails = findViewById(R.id.tvDetails);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnUpdateStatus = findViewById(R.id.btnUpdateStatus);
        btnViewDatabase = findViewById(R.id.btnViewDatabase);
        btnViewCrime = findViewById(R.id.btnViewCrime);

        routesListView = findViewById(R.id.routesListView);
        routesList = new ArrayList<>();
        routesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routesList);
        routesListView.setAdapter(routesAdapter);


        mDatabase = openOrCreateDatabase("KPak.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS distressInfo (location TEXT, details TEXT, status TEXT);");

        // Set up spinner with status options
        String[] arraySpinner = new String[] {
                "Set Status", "En Route", "On The Scene", "Resolved"
        };
        Spinner s = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });

        btnViewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDatabase();
            }
        });

        btnViewCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResponderActivity.this, CrimeDatabase.class);
                startActivity(intent);
            }
        });
        tvLocation.setText("Location: " + location);
        tvDetails.setText("Details: " + details);
    }

    private void updateStatus() {
        String selectedStatus = spinnerStatus.getSelectedItem().toString();
        String forLocation = location;
        String forDetails = details;
        mDatabase.execSQL("INSERT INTO distressInfo (location, details, status) VALUES (?, ?, ?);", new String[] {forLocation, forDetails, selectedStatus});
        Intent intent = new Intent(ResponderActivity.this, ResponderActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Status updated: " + selectedStatus, Toast.LENGTH_SHORT).show();
    }

    private void viewDatabase() {
        // Code to open the database or navigate to the database activity
        routesList.clear();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM distressInfo;", null);
        if (cursor.moveToFirst()) {
            do {
                String location = cursor.getString(0);
                String details = cursor.getString(1);
                String routeWithDelay = location + " - " + details;
                routesList.add(routeWithDelay);
            } while (cursor.moveToNext());
        }
        cursor.close();

        routesAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Viewing database", Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedStatus = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Selected status: " + selectedStatus, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}