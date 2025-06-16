package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bean.UserBean;

import database.DatabaseHandler;

public class RegisterViewActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword,
            edtFirstName, edtLastName,
            edtEmail;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo database handler
        dbHandler = new DatabaseHandler(this);

        // Ánh xạ các trường nhập liệu
        edtUsername = findViewById(R.id.editRegisterUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        edtFirstName = findViewById(R.id.ptFirstName);
        edtLastName = findViewById(R.id.ptLastName);
        edtEmail = findViewById(R.id.ptEmail);

        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username và password không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo user mới (role và campus có thể gán mặc định hoặc thêm EditText nếu cần)
            UserBean newUser = new UserBean(
                    0, // ID để 0, sẽ tự động tăng
                    username,
                    firstName,
                    "",
                    lastName,
                    password,
                    email,
                    "",
                    "user",
                    "main"
            );

            // Lưu user vào database
            dbHandler.insertUser(newUser);

            // Lưu thông tin cần thiết vào SharedPreferences
            SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.apply();

            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish(); // quay lại màn hình trước
        });
    }
}
