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

        // --- Botón de Iniciar Sesión (login) ---
        // Vamos a hacer que este botón nos lleve a la pantalla de PERFIL.
        binding.login.setOnClickListener {
            // Crea un "Intent" para ir desde esta MainActivity hacia PerfilActivity.
            val intent = Intent(this, perfil::class.java)

            // Inicia la nueva actividad.
            startActivity(intent)
        }

        // --- Botón de Registrar Cuenta (registrar) ---
        // Vamos a hacer que este botón nos lleve a la pantalla de PRODUCTOS.
        binding.registrar.setOnClickListener {
            // Crea un "Intent" para ir desde esta MainActivity hacia productos (el nombre de tu clase).
            val intent = Intent(this, productos::class.java)

            // Inicia la nueva actividad.
            startActivity(intent)
        }
    }
}
