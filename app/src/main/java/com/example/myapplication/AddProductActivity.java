package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {

    private EditText edtProductName;
    private EditText edtProductPrice;
    private ProductRepository productRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtProductName=findViewById(R.id.edtAddProductName);
        edtProductPrice=findViewById(R.id.edtAddPrice);
        Button btnsaveProduct=findViewById(R.id.btnSaveAddProduct);
        productRepository= new ProductRepository(this);
        btnsaveProduct.setOnClickListener(v -> {
            String name = edtProductName.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(this, "Product Name not empty",Toast.LENGTH_SHORT).show();

            }
            String price =edtProductPrice.getText().toString();
            if(price.isEmpty()){
                Toast.makeText(this, "Price not null",Toast.LENGTH_SHORT).show();

            }
            double priceProduct=Double.parseDouble(price);
            ProductBean newProduct= new ProductBean();
            newProduct.setName(name);
            newProduct.setPrice(priceProduct);
            productRepository.createProduct(newProduct);
            finish();

        });
    }
}