package com.example.project_3final;

import android.annotation.SuppressLint
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtUsername: EditText = findViewById(R.id.etUsername)
        val edtPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtMessage: TextView = findViewById(R.id.tvError)

        btnLogin.setOnClickListener {
            val inputUsername = edtUsername.text.toString().trim()
            val inputPassword = edtPassword.text.toString()

            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedUsername = prefs.getString("username", "")
            val savedPassword = prefs.getString("password", "")

            if (inputUsername == savedUsername && inputPassword == savedPassword) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProductListActivity::class.java))
            } else {
                txtMessage.text = getString(R.string.tenDangNhapHoacMatKhauSai)
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        val btnToRegister = findViewById<TextView>(R.id.tvRegister)
        btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed(){}
}