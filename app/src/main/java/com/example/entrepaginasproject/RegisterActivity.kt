package com.example.entrepaginasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.entrepaginasproject.api.ApiClient
import com.example.entrepaginasproject.api.LoginResponse
import com.example.entrepaginasproject.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para ir al Login si ya tiene cuenta
        binding.tvIrALogin.setOnClickListener {
            finish() // Simplemente cierra esta actividad y vuelve al Login anterior
        }

        // Botón REGISTRARME
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val email = binding.etEmailReg.text.toString().trim()
            val pass = binding.etPasswordReg.text.toString().trim()
            val confirmPass = binding.etPasswordConfirm.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Llamada a la API
            registerUser(nombre, email, pass)
        }
    }

    private fun registerUser(nombre: String, email: String, pass: String) {
        ApiClient.instance.registrarUsuario(nombre, email, pass)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(applicationContext, "Registro Exitoso", Toast.LENGTH_LONG).show()
                        // Redirigir al Login
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Error: ${response.body()?.message}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}