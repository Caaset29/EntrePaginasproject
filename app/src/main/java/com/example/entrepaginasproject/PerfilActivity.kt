package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.entrepaginasproject.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- 1. RECUPERAR DATOS DE SESIÓN ---
        val session = SessionManager(this)
        val usuario = session.getUser()

        // Mostrar datos en el encabezado (Nombre y Email)
        binding.tvUserName.text = usuario.nombre
        binding.tvUserEmail.text = usuario.email

        // --- 2. BOTÓN "INFORMACIÓN PERSONAL" ---
        // Al hacer clic, abre la actividad PersonalInfoActivity
        binding.btnPersonalInfo.setOnClickListener {
            startActivity(Intent(this, PersonalInfoActivity::class.java))
        }

        // --- 3. BOTÓN "CERRAR SESIÓN" ---
        binding.btnLogout.setOnClickListener {
            session.clearSession() // Borrar datos guardados
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()

            // Volver al Login y borrar historial para que no pueda volver atrás
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // --- 4. BARRA DE NAVEGACIÓN INFERIOR ---
        binding.bottomNavigation.selectedItemId = R.id.nav_profile // Marcar icono "Mi Cuenta"

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    overridePendingTransition(0, 0) // Quitar animación para que se sienta fluido
                    finish()
                    true
                }
                R.id.nav_cart -> {
                    Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_favorites -> {
                    Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> true // Ya estamos aquí
                else -> false
            }
        }
    }
}