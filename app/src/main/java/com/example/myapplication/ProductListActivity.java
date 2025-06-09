package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.ProductBean;

import java.lang.reflect.Method;
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
        fetchProductList();
       //registerForContextMenu(recyclerViewProductList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.menu_setting){
            showSetting();
            return true;
        }
        if (item.getItemId()==R.id.menu_favourite){
            showFavourite();
            return true;
        }
        if (item.getItemId()==R.id.menu_logout){
            finish();

        }
        return false;
    }
    private void showFavourite() {
        Toast.makeText(this, "Favorite is clicked",Toast.LENGTH_SHORT).show();
    }

    private void showSetting() {
        Toast.makeText(this, "Setting is clicked",Toast.LENGTH_SHORT).show();
    }

    private void showDetails() {
        Toast.makeText(this, "details is clicked",Toast.LENGTH_SHORT).show();
    }

    private void showEdit() {
        Toast.makeText(this, "Edit is clicked",Toast.LENGTH_SHORT).show();
    }

    private void showDelete() {

        Toast.makeText(this, "Delete is clicked",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onMenuOpened(int featureId,Menu menu){
        if(menu !=null){
            try{
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu,true);

            }catch(Exception e){
                Log.e("menuOption","error setting oprions icon visible",e);
            }
        }
        return super.onMenuOpened(featureId,menu);
    }
    private void fetchProductList() {
        for(int i=1;i<=10;i++){
            ProductBean product = new ProductBean();
            product.setId(i);
            product.setName("Product"+i);
            product.setPrice(i*10.0);
            productBeanList.add(product);
        }
        productAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        getMenuInflater().inflate(R.menu.menu_context,menu);
        super.onCreateContextMenu(menu, v , menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if (item.getItemId()==R.id.menu_view){
            showDetails();
            return true;
        }
        if (item.getItemId()==R.id.menu_edit){
            showEdit();
            return true;
        }
        if (item.getItemId()==R.id.menu_delete){
            showDelete();
            finish();

        }
        return super.onContextItemSelected(item);
    }



}