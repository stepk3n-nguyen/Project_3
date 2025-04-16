package com.example.project_3final

//object CartManager {
//    private val cartItems = mutableListOf<Product>()
//    fun addToCart(product: Product) {
//        val existingProduct = cartItems.find { it.name == product.name }
//        if (existingProduct != null) {
//            existingProduct.quantity += 1
//        } else {
//            cartItems.add(product.copy())
//        }
//    }
//    fun getCartList(): List<Product> = cartItems
//}

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        val existingProduct = cartItems.find { it.name == product.name }
        if (existingProduct != null) {
            existingProduct.quantity += 1
        } else {
            cartItems.add(product.copy())
        }
    }

    fun getCartList(): List<Product> = cartItems

    fun removeFromCart(product: Product) {
        cartItems.removeIf { it.name == product.name }
    }

    fun getTotalPrice(): Int {
        return cartItems.sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
