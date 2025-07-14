package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
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
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Service.MyBoundService;
import com.example.myapplication.Service.MyUnboundService;
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
    private BroadcastReceiver myBroadCastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        String action =intent.getAction();
        String message= intent.getStringExtra("message");
            Toast.makeText(context,"Action:"+action+ " and Message: "+message, Toast.LENGTH_SHORT).show();

        }
    };

    private ActivityResultLauncher<Intent> editProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null) {
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
        productRepository = new ProductRepository(this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("my_action");
        registerReceiver(myBroadCastReceiver,intentFilter,Context.RECEIVER_EXPORTED);



        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
        productAdapter = new ProductAdapter(productBeanList, this);

        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductList.setAdapter(productAdapter);
        //fetchProductList();
        //registerForContextMenu(recyclerViewProductList);
        getProducts();
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);

        });
    }

    private void getProducts() {
        productBeanList.clear();
        productBeanList.addAll(productRepository.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private MyBoundService myBoundService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            myBoundService = ((MyBoundService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyBoundService.class);
        intent.putExtra("add", 1);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop(){
        super.onStop();
        unbindService(serviceConnection);
    }
    private void showCount(){
        Toast.makeText(this, "Count get from Service: "+myBoundService.getCounter(), Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            showSetting();
            return true;
        }
        if (item.getItemId() == R.id.menu_favourite) {
            showFavourite();
            return true;
        }
        if (item.getItemId() == R.id.menu_logout) {
            finish();

        }
        if (item.getItemId() == R.id.menu_request_gps) {
            Toast.makeText(this, "GPS is clicked", Toast.LENGTH_SHORT).show();
            requestGPS();
        }
        if (item.getItemId() == R.id.menu_send_notification) {
            Toast.makeText(this, "notification is clicked", Toast.LENGTH_SHORT).show();
            sendNotification();
            return true;
        }
        if (item.getItemId() == R.id.menu_start_service) {
            Toast.makeText(this, "Start Service is clicked", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MyUnboundService.class);
            intent.putExtra("msg", "message from activity");
            startService(intent);
        }
        if (item.getItemId() == R.id.menu_count_option) {
            showCount();
        }
        if(item.getItemId() == R.id.menu_send_broadcast){
            Intent intent = new Intent("my_action");
            intent.putExtra("message","Message from activity");
            sendBroadcast(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendNotification() {
        String CHANNEL_ID = "my_Chanel_Id";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_action_favourite)
                        .setContentTitle("My notification")
                        .setContentText("This is your notification text.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(false);

        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtra("message", "this is my notification 1234");
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Demo Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1, builder.build());
    }

    private void showFavourite() {
        Toast.makeText(this, "Favorite is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showSetting() {
        Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showDetails() {
        Toast.makeText(this, "details is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showEdit() {
        Toast.makeText(this, "Edit is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showDelete() {

        Toast.makeText(this, "Delete is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            try {
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);

            } catch (Exception e) {
                Log.e("menuOption", "error setting oprions icon visible", e);
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void fetchProductList() {


        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.productlist));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_view) {
            showDetails();
            return true;
        }
        if (item.getItemId() == R.id.menu_edit) {

            showEdit();
            int position = productAdapter.getSelectedPosition();
            ProductBean selectedProduct = productBeanList.get(position);
            Intent intent = new Intent(ProductListActivity.this, EditProductActivity.class);
            intent.putExtra("ProductId", selectedProduct.getId());
            //startActivity(intent);
            editProductLauncher.launch(intent);
            return true;
        }
        if (item.getItemId() == R.id.menu_delete) {
            showDelete();
            finish();

        }

        return super.onContextItemSelected(item);
    }

    private void requestGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("GPS permission")
                        .setMessage("Please grant GPS")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        })
                        .setCancelable(false);
            } else {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            Toast.makeText(this, "GPS permission is granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu){
//        if(menu!=null){
//            try {
//                Method method = menu.getClass().getDeclaredMethod("setOptional");
//                method.setAccessible(true);
//                method.invoke(menu,true);
//                return true;
//            }catch (Exception e){
//                Log.e("MenuOpions","Error setting options icon visibolty");
//            }
//        }
//        return false;
//    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myBroadCastReceiver);
    }
}