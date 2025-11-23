package com.example.entrepaginasproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.entrepaginasproject.databinding.ActivityPersonalInfoBinding

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalInfoBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        val usuario = session.getUser()

        // Rellenar los campos con los datos de la sesión
        binding.etInfoName.setText(usuario.nombre)
        binding.etInfoAlias.setText(usuario.alias)
        binding.etInfoEmail.setText(usuario.email)
        binding.etInfoPhone.setText(usuario.celular)

        // Botón Atrás
        binding.btnBack.setOnClickListener { finish() }

        // Botón Cerrar
        binding.btnCloseInfo.setOnClickListener { finish() }
    }
}