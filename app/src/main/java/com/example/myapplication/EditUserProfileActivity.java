package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditUserProfileActivity extends AppCompatActivity {
    private EditText editFirstNameProfile;
    private EditText editLastNameProfile;
    private EditText editEmailProfile;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            editFirstNameProfile=findViewById(R.id.ptFirstName1);
            editLastNameProfile=findViewById(R.id.ptLastName1);
            editEmailProfile=findViewById(R.id.ptEmail1);
            btnSave=findViewById(R.id.btnSaveProfile1);
            btnSave.setOnClickListener(v1 -> {
                Intent intent = new Intent();
                intent.putExtra("firstName",editFirstNameProfile.getText().toString());
                intent.putExtra("lastName",editLastNameProfile.getText().toString());
                intent.putExtra("email",editEmailProfile.getText().toString());
                intent.putExtra("address","dia chi tu lay");
                intent.putExtra("MobileNo","0705927599");
                setResult(RESULT_OK,intent);
                finish();
            });
            getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Toast.makeText(EditUserProfileActivity.this,"Back pressed",Toast.LENGTH_SHORT).show();
               // finish();
                }

            });
    }
}