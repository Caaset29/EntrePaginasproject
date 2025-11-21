package com.example.entrepaginasproject.api

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val precio: Double,
    val stock: Int,
    val imagen_url: String?, // Puede ser nulo si no tiene imagen
    val categoria: String?
)