package com.example.project_3final

import android.content.Intent
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.edit
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edUsername = findViewById(R.id.edUsername)
        edPassword = findViewById(R.id.edPassword)
        edConfirmPassword = findViewById(R.id.edConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = edUsername.text.toString().trim()
            val password = edPassword.text.toString()
            val confirm = edConfirmPassword.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Lưu account vào SharedPreferences
            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            prefs.edit() {
                putString("username", username)
                putString("password", password)
                    .apply()
            }

            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val btnToLogin : TextView = findViewById(R.id.tvToLogin)
        btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
