package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "MainActivity";
    private EditText edtUsername;
    private EditText edtPassword;
    private Spinner snCampus;
    private Button btnLogin;
    private RadioButton rbStaff;
    private RadioButton rbManager;
    private CheckBox cbHaha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        startActivity(intent);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        edtUsername = findViewById(R.id.editUsername);
        rbManager = findViewById(R.id.rbManager);
        rbStaff = findViewById(R.id.rbStaff);
        edtPassword = findViewById(R.id.editPassword);
        snCampus = findViewById(R.id.spCampus);
        cbHaha = findViewById(R.id.cbRemember);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.campus,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snCampus.setAdapter(adapter);
        snCampus.setOnItemSelectedListener(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String campus = snCampus.getSelectedItem().toString();
                String role = rbStaff.isChecked() ? "Staff" : rbManager.isChecked() ? "Manager" : "None";
                boolean remmemberMe = cbHaha.isChecked();
                Toast.makeText(MainActivity.this, "username: " + username, Toast.LENGTH_SHORT).show();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "username or passoword need enter ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ViewUserProfile2.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("campus",campus);
                intent.putExtra("role",role);
                intent.putExtra("rememberMe",remmemberMe);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCampus = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "You choose: " + selectedCampus, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "You don't choose any campus", Toast.LENGTH_SHORT).show();
    }
}

