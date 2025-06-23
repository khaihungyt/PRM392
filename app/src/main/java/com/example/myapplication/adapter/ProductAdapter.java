package com.example.myapplication.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.EditProductActivity;
import com.example.myapplication.ProductListActivity;
import com.example.myapplication.R;
import com.example.myapplication.bean.ProductBean;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductBean> productList;
    private Context context;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    private int selectedPosition;
    public ProductAdapter(List<ProductBean> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }


    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_items,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        ProductBean product = productList.get(position);
        holder.tvProductId.setText(String.valueOf(product.getId()));
        holder.tvProductName.setText(product.getName());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvProductId;
        private TextView tvProductName;
        private TextView tvPrice;
        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductId=itemView.findViewById(R.id.tvProdutctId);
            tvProductName=itemView.findViewById(R.id.tvProdutctName);
            tvPrice=itemView.findViewById(R.id.tvProdutctPrice);
            tvPrice.setOnClickListener(this);
            if(context instanceof  ProductListActivity) {
               ((ProductListActivity) context).registerForContextMenu(itemView);
            }
            tvProductName.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
                Toast.makeText(context, "Productname: "+tvProductName.getText(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("confirmation");
                builder.setMessage("Are you sure to delete Product ID:"+ tvProductId.getText())
                        .setPositiveButton("OK",((dialog, which) -> {
                                    productList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    dialog.dismiss();
                                })
                        )
                        .setNegativeButton("Cancel",((dialog, which) -> {
                            dialog.dismiss();
                        }));
                AlertDialog dialog=builder.create();
                dialog.show();
            }

        @Override
        public boolean onLongClick(View v) {
            selectedPosition=getAdapterPosition();
            Toast.makeText(context, "Long Clicked"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
            //((ProductListActivity) context).openContextMenu(v);
            v.showContextMenu();
            return true;
        }
    }
    }

