package com.example.project_3final

object CartManager {
    private val cartItems = mutableListOf<Product>()
    fun addToCart(product: Product) {
        cartItems.add(product)
    }
    fun getCartList(): List<Product> = cartItems
}