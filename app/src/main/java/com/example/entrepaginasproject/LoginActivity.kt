package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.entrepaginasproject.api.ApiClient
import com.example.entrepaginasproject.api.LoginResponse
import com.example.entrepaginasproject.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ir a pantalla de Registro
        binding.tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Botón INGRESAR
        binding.btnIngresar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, pass)
        }
    }

    private fun loginUser(email: String, pass: String) {
        ApiClient.instance.loginUsuario(email, pass)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val resp = response.body()
                    if (response.isSuccessful && resp?.success == true) {
                        val usuario = resp.usuario
                        Toast.makeText(applicationContext, "Bienvenido ${usuario?.nombre}", Toast.LENGTH_LONG).show()

                        // AQUÍ GUARDARÍAS LA SESIÓN (SharedPreferences) SI QUISIERAS

                        // Ir al Catálogo
                        val intent = Intent(this@LoginActivity, CatalogoActivity::class.java)
                        // Limpiar historial para que no pueda volver al login con "Atrás"
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(applicationContext, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}