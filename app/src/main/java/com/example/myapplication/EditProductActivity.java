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

public class EditProductActivity extends AppCompatActivity {
    private EditText EditProductName;
    private EditText EditProductPrice;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int productId = getIntent().getIntExtra("ProductId", -1);
        EditProductName=findViewById(R.id.edtEditProductName);
        EditProductPrice=findViewById(R.id.edtEditProductPrice);
        btnSave=findViewById(R.id.btnEditProduct);
        btnSave.setOnClickListener(v1 -> {
            Intent intent = new Intent();
            intent.putExtra("ProductId",productId);
            intent.putExtra("EditProductName",EditProductName.getText().toString());
            intent.putExtra("EditProductPrice",EditProductPrice.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        });
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(EditProductActivity.this,"Back pressed",Toast.LENGTH_SHORT).show();
                // finish();
            }
        });
    }
}