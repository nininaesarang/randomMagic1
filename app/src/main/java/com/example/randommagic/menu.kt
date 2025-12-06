package com.example.randommagic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randommagic.databinding.ActivityMenuBinding

class menu : AppCompatActivity() {// Declara la variable para ViewBinding. 'lateinit' significa que la inicializaremos más tarde.
private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. "Inflar" el layout XML y prepararlo para su uso con ViewBinding.
        binding = ActivityMenuBinding.inflate(layoutInflater)

        // 2. Establecer el layout inflado como la vista de esta actividad.
        setContentView(binding.root)

        // 3. Configurar los listeners para los botones.
        configurarBotones()
    }

    private fun configurarBotones() {

        binding.chat.setOnClickListener {
            val intent = Intent(this, chat::class.java)
            startActivity(intent)
        }

        binding.perfil.setOnClickListener {
            // Crea un "Intent" para ir desde esta MainActivity hacia productos (el nombre de tu clase).
            val intent = Intent(this, registrarUsuario::class.java)

            // Inicia la nueva actividad.
            startActivity(intent)
        }

        binding.productos.setOnClickListener {
            val intent = Intent(this, productos::class.java)
            startActivity(intent)
        }
    }
}