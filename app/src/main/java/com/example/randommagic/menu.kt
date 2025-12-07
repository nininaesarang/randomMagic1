package com.example.randommagic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.randommagic.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth

class menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ¡IMPORTANTE! Inicializar la instancia de Firebase Auth.
        auth = FirebaseAuth.getInstance()

        // Llamar a las funciones de configuración.
        configurarBotones()
        mostrarBienvenida() // Para que el mensaje aparezca.
    }

    private fun configurarBotones() {
        binding.chat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        binding.perfil.setOnClickListener {
            // Tu clase se llama 'perfil', no PerfilActivity
            val intent = Intent(this, perfil::class.java)
            startActivity(intent)
        }

        binding.productos.setOnClickListener {
            val intent = Intent(this, productos::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun mostrarBienvenida() {
        val user = auth.currentUser
        binding.bienvenida.text = "Bienvenido, ${user?.email ?: "Invitado"}"
    }

    private fun cerrarSesion() {
        auth.signOut()

        // La corrección clave: apuntar a MainActivity.
        val intent = Intent(this, MainActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
