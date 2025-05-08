package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.NumberFormat
import java.util.Locale

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

        val recyclerView: RecyclerView = findViewById(R.id.recyclerCart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cartItems = CartManager.getCartList()
        recyclerView.adapter = CartAdapter(cartItems, onCartChanged = {recreate()})

        val txtTotalPrice: TextView = findViewById(R.id.tvTotalPrice)
        val totalPrice = CartManager.getCartList().sumOf { it.price * it.quantity }
        //------------Sơ cua-------------------
        val tvEmptyCart: TextView = findViewById(R.id.tvEmptyCart)
        val imgEmptyCart: ImageView = findViewById(R.id.imgEmptyCart)
        //------------Sơ cua-------------------
        val emptyCart: LinearLayout = findViewById(R.id.emptyCart)
        if (cartItems.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyCart.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyCart.visibility = View.GONE
        }

        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
        txtTotalPrice.text = "Tổng cộng: $formattedPrice ₫"

        val btnCheckout: Button = findViewById(R.id.btnCheckout)
        btnCheckout.setOnClickListener {
            val cartItems = CartManager.getCartList()
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show()
            } else {
                val totalPrice = CartManager.getTotalPrice()
                val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
                Toast.makeText(this, "Thanh toán thành công! Tổng tiền: ${formattedPrice}₫", Toast.LENGTH_LONG).show()

                CartManager.clearCart()

                recyclerView.adapter = CartAdapter(emptyList(), onCartChanged = {recreate()})
                txtTotalPrice.text = "Tổng cộng: 0₫"
            }
        }

        val adapter = CartAdapter(
            cartItems,
            onCartChanged = {
                recreate()
            }
        )
        recyclerView.adapter = adapter
    }
}