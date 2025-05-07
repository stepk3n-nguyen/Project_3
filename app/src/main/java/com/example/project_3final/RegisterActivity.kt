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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        edUsername = findViewById(R.id.edUsername)
        edPassword = findViewById(R.id.edPassword)
        edConfirmPassword = findViewById(R.id.edConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = edUsername.text.toString().trim()
            val password = edPassword.text.toString()
            val confirm = edConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // lưu account vào SharedPreferences
//            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//            prefs.edit() {
//                putString("username", username)
//                putString("password", password)
//                    .apply()
//            }
//
//            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
//            finish()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        val message = task.exception?.message ?: "Lỗi không xác định"
                        Toast.makeText(this, "Đăng ký thất bại: $message", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val btnToLogin : TextView = findViewById(R.id.tvToLogin)
        btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
