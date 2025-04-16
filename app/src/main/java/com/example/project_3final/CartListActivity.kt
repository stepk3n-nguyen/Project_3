package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class CartListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backBtn : TextView = findViewById(R.id.btnBack)
        backBtn.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val logoutBtn : Button = findViewById(R.id.btnLogout)
        logoutBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerCart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cartItems = CartManager.getCartList()
        recyclerView.adapter = CartAdapter(cartItems)
    }
}