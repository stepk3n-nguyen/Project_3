package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val imgProduct: ImageView = findViewById(R.id.imgProduct)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvPrice: TextView = findViewById(R.id.tvPrice)

        val name = intent.getStringExtra("name")
        val price = intent.getIntExtra("price", 0)
        val imageRes = intent.getIntExtra("image", 0)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(price)
        tvPrice.text = "Giá: $formattedPrice ₫"
        tvName.text = name
        imgProduct.setImageResource(imageRes)

        val btnBack : TextView = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnViewCart : ImageView = findViewById(R.id.btnViewCart)
        btnViewCart.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))
        }
    }
}
