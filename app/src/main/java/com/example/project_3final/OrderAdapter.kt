package com.example.project_3final

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_3final.databinding.ItemOrderBinding
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(order.totalPrice)
        holder.binding.tvOrderId.text = "Mã đơn: ${order.timestamp}"
        holder.binding.tvTotalPrice.text = "Tổng tiền: ${formattedPrice} VND"

        holder.binding.layoutItems.removeAllViews()
        for (item in order.items) {
            val tv = TextView(holder.itemView.context)
            val formattedItemPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(item.price)
            tv.text = "- ${item.name} x ${item.quantity} = (${formattedItemPrice} VND)"
            holder.binding.layoutItems.addView(tv)
        }
    }

    override fun getItemCount(): Int = orders.size
}
