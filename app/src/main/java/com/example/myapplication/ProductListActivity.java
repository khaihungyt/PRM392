package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.repository.ProductRepository;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductListActivity extends AppCompatActivity {
private RecyclerView recyclerViewProductList;
private ProductAdapter productAdapter;
private ProductRepository productRepository;
private Button btnAddProduct;
private List<ProductBean> productBeanList = new ArrayList<>();
    private ActivityResultLauncher<Intent> editProductLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data =result.getData();

                        if(data!=null){
                            int productId = data.getIntExtra("ProductId", -1);
                            String updatedName = data.getStringExtra("EditProductName");
                            double updatedPrice = Double.parseDouble(data.getStringExtra("EditProductPrice"));
                            for (ProductBean product : productBeanList) {
                                if (product.getId() == productId) {
                                    product.setName(updatedName);
                                    product.setPrice(updatedPrice);
                                    productRepository.updateProduct(product);
                                    productAdapter.notifyDataSetChanged();
                                    break; // đã tìm thấy thì không cần lặp nữa
                                }
                            }
                        }
                    }
                }
            });
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
        productRepository=new ProductRepository(this);
        recyclerViewProductList=findViewById(R.id.recyclerViewProductList);
        productAdapter=new ProductAdapter(productBeanList,this);

        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductList.setAdapter(productAdapter);
        //fetchProductList();
       //registerForContextMenu(recyclerViewProductList);
        getProducts();
        btnAddProduct=findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);

        });
    }
    private void getProducts(){
        productBeanList.clear();
        productBeanList.addAll(productRepository.getAllProducts());
        productAdapter.notifyDataSetChanged();
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
        if (item.getItemId()==R.id.menu_request_gps){
            Toast.makeText(this, "GPS is clicked",Toast.LENGTH_SHORT).show();
            requestGPS();
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


        Scanner scanner =new Scanner(getResources().openRawResource(R.raw.productlist));
        while(scanner.hasNextLine()){
            String line =scanner.nextLine();
            String[] parts =line.split(",");
            if(parts.length==3){
                ProductBean product = new ProductBean();
                product.setId(Integer.parseInt(parts[0]));
                product.setName(parts[1]);
                product.setPrice(Double.parseDouble(parts[2]));
                productBeanList.add(product);
            }
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
            int position=productAdapter.getSelectedPosition();
            ProductBean selectedProduct= productBeanList.get(position);
            Intent intent =new Intent(ProductListActivity.this, EditProductActivity.class);
            intent.putExtra("ProductId",selectedProduct.getId());
            //startActivity(intent);
            editProductLauncher.launch(intent);
            return true;
        }
        if (item.getItemId()==R.id.menu_delete){
            showDelete();
            finish();

        }

        return super.onContextItemSelected(item);
    }

    private void requestGPS() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("GPS permission")
                        .setMessage("Please grant GPS")
                        .setPositiveButton("OK",(dialog, which) -> {
                            dialog.dismiss();
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
                        })
                        .setCancelable(false);
            }else{
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
        }else{
            Toast.makeText(this, "GPS permission is granted",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permission,grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "GPS permission granted",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}