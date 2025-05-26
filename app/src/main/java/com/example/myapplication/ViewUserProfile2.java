package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewUserProfile2 extends AppCompatActivity {
    private TextView tvFirstName;

    private TextView tvEmail;
    private TextView tvAddress;
    private TextView tvMobileNo;

    private TextView tvLastName;

    private ActivityResultLauncher<Intent> editProfileLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent data =result.getData();
                if(data!=null){
                    String firstName=data.getStringExtra("firstName");
                    String lastName =data.getStringExtra("lastName");
                    String mobileNo= data.getStringExtra("MobileNo");
                    String email= data.getStringExtra("email");
                    String address= data.getStringExtra("address");
                    tvFirstName.setText(firstName);
                    tvLastName.setText(lastName);
                    tvMobileNo.setText(mobileNo);
                    tvEmail.setText(email);
                    tvAddress.setText(address);
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user_profile2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvFirstName=findViewById(R.id.tvFirstName);
        tvLastName=findViewById(R.id.tvLastName);
        tvMobileNo=findViewById(R.id.tvPhoneNo);
        tvMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tvMobileNo.getText().toString()));
                startActivity(intent);
            }
        });

        if(getIntent() !=null){
            String username=getIntent().getStringExtra("username");
            tvFirstName.setText("username: "+username);
        }
        tvEmail= findViewById(R.id.tvEmail);
        tvAddress=findViewById(R.id.tvAddress);

        Button btnEditProfile = findViewById(R.id.btnEdit);
        btnEditProfile.setOnClickListener(v ->{
            Intent intent =new Intent(ViewUserProfile2.this,EditUserProfileActivity.class);
            intent.putExtra("username",tvFirstName.getText().toString());
            //startActivityForResult(intent,2);
            editProfileLauncher.launch(intent);
        });
    }
}