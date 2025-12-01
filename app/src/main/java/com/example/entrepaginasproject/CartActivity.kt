package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entrepaginasproject.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val SHIPPING_COST = 15.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        updateSummary()
        setupButtons()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)

        // Pasamos una función lambda para que el adaptador nos avise cuando cambiar los totales
        adapter = CartAdapter(CartRepository.getCartItems()) {
            updateSummary()
        }
        binding.rvCartItems.adapter = adapter
    }

    private fun updateSummary() {
        val subtotal = CartRepository.getTotalPrice()
        val total = subtotal + SHIPPING_COST
        val count = CartRepository.getItemCount()

        binding.tvSubtotal.text = "S/ ${String.format("%.2f", subtotal)}"
        binding.tvTotal.text = "S/ ${String.format("%.2f", total)}"
        binding.tvCartHeader.text = "Tu carrito de compras ($count items)"
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnCheckout.setOnClickListener {
            if (CartRepository.getCartItems().isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Aquí iría la lógica para ENVIAR EL PEDIDO A LA API PHP
            Toast.makeText(this, "Procesando compra...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_cart
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_cart -> true // Ya estamos aquí
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}