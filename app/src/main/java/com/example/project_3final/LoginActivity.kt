package com.example.project_3final;

import android.annotation.SuppressLint
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val correctUsername = "admin"
        val correctPassword = "1"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtUsername: EditText = findViewById(R.id.etUsername)
        val edtPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtMessage: TextView = findViewById(R.id.tvError)

        btnLogin.setOnClickListener {
            val inputUsername = edtUsername.text.toString()
            val inputPassword = edtPassword.text.toString()
            if (inputUsername == correctUsername && inputPassword == correctPassword) {
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                txtMessage.text = getString(R.string.tenDangNhapHoacMatKhauSai)
            }
        }
    }
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed(){}
}