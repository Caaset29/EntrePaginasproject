package com.example.entrepaginasproject.api

import java.io.Serializable

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val precio: Double,
    val stock: Int,
    val imagen_url: String?, // Puede ser nulo si no tiene imagen
    val categoria: String?,
    val descripcion: String?
): Serializable