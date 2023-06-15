package com.example.kpak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView forErrors;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mRegisteredButton;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        mRegisteredButton = findViewById(R.id.btnRegister);
        forErrors = findViewById(R.id.textviewError);

        mDatabase = openOrCreateDatabase("KPak.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS userInfo (username TEXT, password TEXT, type TEXT);");

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                Cursor cursor = mDatabase.rawQuery("SELECT * FROM userInfo WHERE username=? AND password=?;", new String[] {username, password});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int typeIndex = cursor.getColumnIndex("type");
                    if (typeIndex != -1) {
                        String userType = cursor.getString(typeIndex);
                        if (userType.equals("Citizen")) {
                            Intent intent = new Intent(MainActivity.this, CitizenActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, ResponderActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        // Handle the case when the "type" column is not found
                        forErrors.setText("USER TYPE COLUMN NOT FOUND.");
                    }
                } else {
                    forErrors.setText("USER DOES NOT EXIST.");
                }
                cursor.close();
            }
        });

        mRegisteredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register_activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        mDatabase.close();
        super.onDestroy();
    }
}