package com.example.entrepaginasproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.entrepaginasproject.api.Libro
import com.example.entrepaginasproject.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val BASE_URL_IMG = "http://192.168.1.58/" // Ajusta según tu IP si usas celular físico

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

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAddToCart.setOnClickListener {
            Toast.makeText(this, "Añadido al carrito (Próximamente)", Toast.LENGTH_SHORT).show()
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
}