package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.entrepaginasproject.api.Libro
import com.example.entrepaginasproject.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val BASE_URL_IMG = "http://192.168.1.58/" // Ajusta según tu IP si usas celular físico
    private var currentBook: Libro? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Recibir el objeto Libro enviado desde el Adapter
        val libro = intent.getSerializableExtra("EXTRA_LIBRO") as? Libro

        if (libro != null) {
            mostrarDatos(libro)
        } else {
            Toast.makeText(this, "Error al cargar el libro", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 1. Recibir el objeto Libro enviado desde el Catálogo
        currentBook = intent.getSerializableExtra("EXTRA_LIBRO") as? Libro

        if (currentBook != null) {
            mostrarDatos(currentBook!!)
            checkFavoriteStatus() // Verificar si ya estaba en favoritos
        } else {
            Toast.makeText(this, "Error al cargar el libro", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnFavoriteToggle.setOnClickListener {
            toggleFavorite()
        }

        binding.btnAddToCart.setOnClickListener {
            if (currentBook != null) {
                CartRepository.addItem(currentBook!!)
                Toast.makeText(this, "Libro agregado al carrito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDatos(libro: Libro) {
        binding.tvDetailTitle.text = libro.titulo
        binding.tvDetailAuthor.text = libro.autor
        binding.tvDetailPrice.text = "S/ ${String.format("%.2f", libro.precio)}"
        binding.tvDetailStock.text = "Stock: ${libro.stock}"

        // Mostrar descripción (o texto por defecto si viene vacía)
        binding.tvDetailDesc.text = if (!libro.descripcion.isNullOrEmpty()) libro.descripcion else "Sin descripción disponible."

        // Cargar Imagen
        if (!libro.imagen_url.isNullOrEmpty()) {
            val fullUrl = BASE_URL_IMG + libro.imagen_url
            Glide.with(this)
                .load(fullUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.imgDetailCover)
        }
    }

    // Verifica si el libro existe en el repositorio y actualiza el icono visualmente
    private fun checkFavoriteStatus() {
        if (currentBook == null) return

        isFavorite = FavoritesRepository.isFavorite(currentBook!!)
        updateFavoriteIcon()
    }

    // Agrega o quita el libro del repositorio y muestra mensaje
    private fun toggleFavorite() {
        if (currentBook == null) return

        if (isFavorite) {
            FavoritesRepository.removeBook(currentBook!!)
            Toast.makeText(this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show()
            isFavorite = false
        } else {
            FavoritesRepository.addBook(currentBook!!)
            Toast.makeText(this, "Agregado a Favoritos", Toast.LENGTH_SHORT).show()
            isFavorite = true
        }
        updateFavoriteIcon()
    }

    // Cambia el dibujo del corazón (Relleno vs Borde)
    private fun updateFavoriteIcon() {
        val colorRojo = ContextCompat.getColor(this, R.color.red_primary)

        if (isFavorite) {
            // Corazón Relleno (Asegúrate de tener ic_favorite en drawable)
            binding.ivFavoriteIcon.setImageResource(R.drawable.ic_favorite)
            binding.ivFavoriteIcon.setColorFilter(colorRojo)
        } else {
            // Corazón Borde (Asegúrate de tener ic_heart_outline en drawable)
            binding.ivFavoriteIcon.setImageResource(R.drawable.ic_heart_outline)
            binding.ivFavoriteIcon.setColorFilter(colorRojo)
        }
    }

}