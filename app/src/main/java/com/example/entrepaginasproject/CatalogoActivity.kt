package com.example.entrepaginasproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entrepaginasproject.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí implementarás luego la carga de libros en el RecyclerView
    }
}