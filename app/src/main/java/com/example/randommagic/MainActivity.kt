package com.example.randommagic // Asegúrate de que el paquete sea el correcto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randommagic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {// Declara la variable para ViewBinding. 'lateinit' significa que la inicializaremos más tarde.
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. "Inflar" el layout XML y prepararlo para su uso con ViewBinding.
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 2. Establecer el layout inflado como la vista de esta actividad.
        setContentView(binding.root)

        // 3. Configurar los listeners para los botones.
        configurarListenersDeBotones()
    }

    private fun configurarListenersDeBotones() {

        binding.login.setOnClickListener {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
        }

        binding.registrar.setOnClickListener {
            // Crea un "Intent" para ir desde esta MainActivity hacia productos (el nombre de tu clase).
            val intent = Intent(this, registrarUsuario::class.java)

            // Inicia la nueva actividad.
            startActivity(intent)
        }
    }
}
