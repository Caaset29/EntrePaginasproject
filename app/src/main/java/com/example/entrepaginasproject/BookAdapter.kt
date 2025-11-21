package com.example.entrepaginasproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.entrepaginasproject.api.Libro
import com.example.entrepaginasproject.databinding.ItemBookBinding

class BookAdapter(private val libros: List<Libro>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // URL base de tu servidor para las imágenes (Ajusta si usas celular físico)
    private val BASE_URL_IMG = "http://192.168.1.58/"

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // Aquí "inflamos" (cargamos) el diseño de item_book.xml
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val libro = libros[position]

        // Asignar textos
        holder.binding.tvBookTitle.text = libro.titulo
        holder.binding.tvBookAuthor.text = libro.autor
        holder.binding.tvBookPrice.text = "S/ ${String.format("%.2f", libro.precio)}"

        // Cargar imagen con Glide
        if (!libro.imagen_url.isNullOrEmpty()) {
            // Construimos la URL completa: http://192.168.1.58/entrepaginas_api/imagenes/foto.jpg
            val fullUrl = BASE_URL_IMG + libro.imagen_url

            Glide.with(holder.itemView.context)
                .load(fullUrl)
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagen de carga
                .error(android.R.drawable.ic_dialog_alert) // Imagen si falla
                .into(holder.binding.imgBookCover)
        }
    }

    override fun getItemCount(): Int = libros.size
}