package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.entrepaginasproject.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Configurar RecyclerView (Grid de 2 columnas)
        binding.rvFavoriteBooks.layoutManager = GridLayoutManager(this, 2)

        // Inicializamos el adaptador vacío. Los datos se cargarán en onResume
        adapter = BookAdapter(emptyList())
        binding.rvFavoriteBooks.adapter = adapter

        // 2. Botón Atrás
        binding.btnBack.setOnClickListener { finish() }

        // 3. Configurar Barra de Navegación
        setupBottomNavigation()
    }

    // onResume se ejecuta cada vez que la actividad aparece en pantalla.
    // Es el lugar perfecto para recargar la lista por si agregaste/quitaste favoritos.
    override fun onResume() {
        super.onResume()
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        // Obtenemos la lista actualizada desde el repositorio en memoria
        val listaFavoritos = FavoritesRepository.getFavorites()

        if (listaFavoritos.isEmpty()) {
            Toast.makeText(this, "Aún no tienes libros favoritos", Toast.LENGTH_SHORT).show()
        }

        // Actualizamos el adaptador existente con la nueva lista
        adapter.actualizarLista(listaFavoritos)
    }

    private fun setupBottomNavigation() {
        // Marcamos el icono "Favoritos" como seleccionado
        binding.bottomNavigation.selectedItemId = R.id.nav_favorites

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    overridePendingTransition(0, 0) // Transición suave
                    finish() // Cerramos esta actividad
                    true
                }
                R.id.nav_favorites -> true // Ya estamos aquí, no hacemos nada
                R.id.nav_cart -> {
                    Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}