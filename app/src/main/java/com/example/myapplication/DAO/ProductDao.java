package com.example.myapplication.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Product;

import java.util.List;

public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);
    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update (Product product);
    @Delete
    void delete(Product product);
    @Query("SELECT * FROM products")
    List<Product> findAllProducts();
    @Query("SELECT * FROM products WHERE id= :id")
    Product findById(int id);
    @Query("SELECT * FROM products WHERE name LIKE :name")

    List<Product> findByName(String name);
}
