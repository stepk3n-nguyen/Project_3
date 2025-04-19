package com.example.project_3final

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.NumberFormat
import java.util.Locale
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnBuy: Button = view.findViewById(R.id.btnBuy)
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.txtName.text = product.name
        holder.txtPrice.text = product.price.toString()
        holder.imgProduct.setImageResource(product.imageResId)

        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(product.price)
        holder.txtPrice.text = "$formattedPrice ₫"

        holder.btnBuy.setOnClickListener {
            CartManager.addToCart(product)
            Toast.makeText(holder.itemView.context, "Đã thêm vào giỏ: ${product.name}", Toast.LENGTH_SHORT).show()
        }

        holder.imgProduct.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("name", product.name)
            intent.putExtra("price", product.price)
            intent.putExtra("image", product.imageResId)
            context.startActivity(intent)
        }
    }
    override fun getItemCount() = productList.size
}