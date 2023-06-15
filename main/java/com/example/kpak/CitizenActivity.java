package com.example.kpak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ImageView;

public class CitizenActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CALL = 1;

    private Button btnDistressSignal;
    private Button btnViewEmergencyServices;
    private Button btnSafetyTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);

        btnDistressSignal = findViewById(R.id.btnDistressSignal);
        btnViewEmergencyServices = findViewById(R.id.btnViewEmergencyServices);
        btnSafetyTips = findViewById(R.id.btnSafetyTips);

        btnDistressSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDistressSignal();
            }
        });

        btnViewEmergencyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapForEmergencyServices();
            }
        });

        btnSafetyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSafetyTips();
            }
        });
    }
    private void sendDistressSignal() {
        Toast.makeText(this, "Distress signal sent!", Toast.LENGTH_SHORT).show();
    }

    private void openMapForEmergencyServices() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Emergency Services Map");

        View dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_map, null);

        ImageView imageViewMap = dialogLayout.findViewById(R.id.imageViewMap);

        imageViewMap.setImageResource(R.drawable.maps); // Replace `your_image` with the actual image resource

        builder.setView(dialogLayout);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void openSafetyTips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Safety Tips");

        View dialogLayout = LayoutInflater.from(this).inflate(R.layout.safety_tips, null);

        builder.setView(dialogLayout);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(this, "Opening safety tips", Toast.LENGTH_SHORT).show();
    }
}