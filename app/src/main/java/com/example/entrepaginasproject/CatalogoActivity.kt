package com.example.entrepaginasproject

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.entrepaginasproject.api.ApiClient
import com.example.entrepaginasproject.api.Libro
import com.example.entrepaginasproject.databinding.ActivityCatalogoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding

    // Variables para manejar los datos
    private var listaCompletaLibros: List<Libro> = emptyList() // Copia maestra
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        cargarLibrosDesdeApi()
        setupCategoryListeners()
    }

    private fun setupRecyclerView() {
        binding.rvBooks.layoutManager = GridLayoutManager(this, 2)
        // Inicializamos con lista vacía
        adapter = BookAdapter(emptyList())
        binding.rvBooks.adapter = adapter
    }

    private fun cargarLibrosDesdeApi() {
        ApiClient.instance.obtenerLibros().enqueue(object : Callback<List<Libro>> {
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if (response.isSuccessful) {
                    // 1. Guardamos TODOS los libros en la variable maestra
                    listaCompletaLibros = response.body() ?: emptyList()

                    // 2. Mostramos todo al inicio (o puedes filtrar por defecto)
                    adapter.actualizarLista(listaCompletaLibros)

                    // Opcional: Marcar "Novedades" como activo visualmente si deseas
                    // actualizarEstiloBotones(binding.tvCatNovedades)
                } else {
                    Toast.makeText(applicationContext, "Error al cargar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupCategoryListeners() {
        // Configurar clicks de cada categoría

        binding.tvCatNovedades.setOnClickListener {
            filtrarPorCategoria("Novedades")
            actualizarEstiloBotones(binding.tvCatNovedades)
        }

        binding.tvCatEducativo.setOnClickListener {
            // Nota: Asegúrate que en tu BD la categoría se llame EXACTAMENTE así
            // O filtra por otro criterio si "Más vendidos" no es una categoría real en la BD
            filtrarPorCategoria("Los más vendidos")
            actualizarEstiloBotones(binding.tvCatEducativo)
        }

        binding.tvCatFiccion.setOnClickListener {
            filtrarPorCategoria("Ciencia Ficción")
            actualizarEstiloBotones(binding.tvCatFiccion)
        }

        binding.tvCatFantasia.setOnClickListener {
            filtrarPorCategoria("Fantasía")
            actualizarEstiloBotones(binding.tvCatFantasia)
        }
    }

    private fun filtrarPorCategoria(categoriaSeleccionada: String) {
        // Creamos una sub-lista filtrando la maestra
        val listaFiltrada = listaCompletaLibros.filter { libro ->
            // Comparamos ignorando mayúsculas/minúsculas para evitar errores
            libro.categoria?.equals(categoriaSeleccionada, ignoreCase = true) == true
        }

        if (listaFiltrada.isEmpty()) {
            Toast.makeText(this, "No hay libros en $categoriaSeleccionada", Toast.LENGTH_SHORT).show()
        }

        // Actualizamos el adaptador con la nueva lista filtrada
        adapter.actualizarLista(listaFiltrada)
    }

    // Función visual para resaltar el botón seleccionado
    private fun actualizarEstiloBotones(botonSeleccionado: TextView) {
        // 1. Resetear todos a estilo "inactivo" (Fondo blanco, texto rojo)
        val todosLosBotones = listOf(
            binding.tvCatNovedades,
            binding.tvCatEducativo,
            binding.tvCatFiccion,
            binding.tvCatFantasia
        )

        todosLosBotones.forEach { btn ->
            btn.setBackgroundResource(R.drawable.bg_category_chip) // Tu fondo con borde rojo
            btn.setTextColor(ContextCompat.getColor(this, R.color.red_primary))
        }

        // 2. Poner el seleccionado en estilo "activo" (Fondo rojo, texto blanco)
        // Para esto, podrías crear un drawable "bg_category_chip_selected.xml"
        // O simplemente cambiar el color de fondo por código:
        botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.red_primary))
        botonSeleccionado.setTextColor(Color.WHITE)
    }
}