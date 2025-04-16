package com.example.project_3final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val cartItems: List<Product>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtCartName)
        val txtPrice: TextView = view.findViewById(R.id.txtCartPrice)
        val txtQty: TextView = view.findViewById(R.id.txtCartQty)
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
        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(product.price)
        holder.txtPrice.text = "$formattedPrice ₫"
    }

    override fun getItemCount() = cartItems.size
}
