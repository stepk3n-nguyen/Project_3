package com.example.project_3final

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val cartItems: List<Product>, private val onCartChanged: () -> Unit)
    : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtCartName)
        val txtPrice: TextView = view.findViewById(R.id.txtCartPrice)
        val txtQty: TextView = view.findViewById(R.id.txtCartQty)
        val btnIncrease: TextView= itemView.findViewById<Button>(R.id.btnIncrease)
        val btnDecrease: TextView = itemView.findViewById<Button>(R.id.btnDecrease)
        val imgView: ImageView = itemView.findViewById(R.id.imgProduct)
        val btnDelete: TextView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.txtName.text = product.name
        holder.txtPrice.text = product.price.toString()
        holder.txtQty.text = product.quantity.toString()
        holder.imgView.setImageResource(product.imageResId)
        holder.btnDelete.setOnClickListener {
            CartManager.removeFromCart(product)
            Toast.makeText(holder.itemView.context, "Đã xóa khỏi giỏ: ${product.name}", Toast.LENGTH_SHORT).show()
            onCartChanged()
        }
        holder.btnIncrease.setOnClickListener {
            product.quantity++
            onCartChanged()
        }

        holder.btnDecrease.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                onCartChanged()
            }
        }

        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(product.price)
        holder.txtPrice.text = "$formattedPrice ₫"
    }

    override fun getItemCount() = cartItems.size
}
