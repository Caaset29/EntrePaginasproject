package com.example.entrepaginasproject

import com.example.entrepaginasproject.api.Libro

// Objeto para manejar un item del carrito con cantidad
data class CartItem(
    val libro: Libro,
    var cantidad: Int = 1
)

object CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(book: Libro) {
        val existingItem = cartItems.find { it.libro.id == book.id }
        if (existingItem != null) {
            existingItem.cantidad++
        } else {
            cartItems.add(CartItem(book))
        }
    }

    fun removeItem(book: Libro) {
        cartItems.removeAll { it.libro.id == book.id }
    }

    fun updateQuantity(book: Libro, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(book)
        } else {
            val item = cartItems.find { it.libro.id == book.id }
            item?.cantidad = newQuantity
        }
    }

    fun getCartItems(): List<CartItem> = cartItems

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.libro.precio * it.cantidad }
    }

    fun getItemCount(): Int = cartItems.sumOf { it.cantidad }

    fun clearCart() {
        cartItems.clear()
    }
}