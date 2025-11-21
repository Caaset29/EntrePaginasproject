package com.example.entrepaginasproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.entrepaginasproject.api.ApiClient
import com.example.entrepaginasproject.api.Libro
import com.example.entrepaginasproject.databinding.ActivityCatalogoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el RecyclerView (Grid de 2 columnas)
        binding.rvBooks.layoutManager = GridLayoutManager(this, 2)

        // Llamar a la API para cargar libros
        cargarLibros()
    }

    private fun cargarLibros() {
        // Asegúrate de que obtenerLibros() en ApiService devuelva Call<List<Libro>>
        ApiClient.instance.obtenerLibros().enqueue(object : Callback<List<Libro>> {

            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if (response.isSuccessful) {
                    val listaLibros = response.body() ?: emptyList()
                    binding.rvBooks.adapter = BookAdapter(listaLibros)
                } else {
                    Toast.makeText(applicationContext, "Error al obtener libros", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}