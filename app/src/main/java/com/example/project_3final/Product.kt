package com.example.project_3final

data class Product(
    val name: String,
    val price: Int,
    val imageResId: Int,
    var quantity: Int = 1
)