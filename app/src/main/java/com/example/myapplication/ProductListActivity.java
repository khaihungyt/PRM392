package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
private RecyclerView recyclerViewProductList;
private ProductAdapter productAdapter;
private List<ProductBean> productBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewProductList=findViewById(R.id.recyclerViewProductList);
        productAdapter=new ProductAdapter(productBeanList,this);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductList.setAdapter(productAdapter);
        fetchProfuctList();
    }

    private void fetchProfuctList() {
        for(int i=1;i<=10;i++){
            ProductBean product = new ProductBean();
            product.setId(i);
            product.setName("Product"+i);
            product.setPrice(i*10.0);
            productBeanList.add(product);
        }
        productAdapter.notifyDataSetChanged();
    }
}