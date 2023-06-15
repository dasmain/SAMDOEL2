package com.example.kpak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class register_activity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private TextView mErrorView;
    private Button mRegisterButton;
    private RadioGroup mRadioGroupType;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.editTextTextPersonName);
        mPassword = findViewById(R.id.editTextTextPassword);
        mConfirmPassword = findViewById(R.id.editTextTextPassword2);
        mRadioGroupType = findViewById((R.id.radioGroup));
        mErrorView = findViewById(R.id.textView13);

        mRegisterButton = findViewById(R.id.button);
        mDatabase = openOrCreateDatabase("KPak.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS userInfo (username TEXT, password TEXT, type TEXT);");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String accountType = "";

                int selectedId = mRadioGroupType.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    accountType = radioButton.getText().toString();
                }

                if (!password.equals(confirmPassword)) {
                    mErrorView.setText("Passwords do not match.");
                } else {
                    mDatabase.execSQL("INSERT INTO userInfo (username, password, type) VALUES (?, ?, ?);", new String[] {username, password, accountType});
                    Intent intent = new Intent(register_activity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}