package com.example.project_3final

data class OrderItem(
    val name: String = "",
    val price: Int = 0,
    val quantity: Int = 1   
)

data class Order(
    val userId: String = "",
    val customerName: String = "",
    val address: String = "",
    val totalPrice: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val items: List<OrderItem> = listOf()
)