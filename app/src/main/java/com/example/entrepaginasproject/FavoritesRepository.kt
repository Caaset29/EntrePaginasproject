package com.example.entrepaginasproject

import com.example.entrepaginasproject.api.Libro

object FavoritesRepository {
    // Lista mutable privada para guardar los libros
    private val favoriteBooks = mutableListOf<Libro>()

    // Agregar libro (evitando duplicados)
    fun addBook(book: Libro) {
        if (!favoriteBooks.any { it.id == book.id }) {
            favoriteBooks.add(book)
        }
    }

    // Eliminar libro
    fun removeBook(book: Libro) {
        favoriteBooks.removeAll { it.id == book.id }
    }

    // Obtener la lista para el adaptador
    fun getFavorites(): List<Libro> {
        return favoriteBooks
    }

    // Verificar si ya es favorito (para pintar el corazón)
    fun isFavorite(book: Libro): Boolean {
        return favoriteBooks.any { it.id == book.id }
    }
}