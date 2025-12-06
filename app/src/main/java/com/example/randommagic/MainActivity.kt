package com.example.randommagic // Asegúrate de que el paquete sea el correcto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.randommagic.ChatActivity
import com.example.randommagic.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // 1. Verificar si el usuario ya está logueado
        if (auth.currentUser != null) {
            // Si ya hay sesión, saltar directamente al Chat
            goToChat()
        }

        // 2. Configurar listeners de botones
        binding.login.setOnClickListener {
            loginUser()
        }

        binding.registrar.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.correo.text.toString().trim()
        val password = binding.contra.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe ingresar email y contraseña.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso. ¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    goToChat()
                } else {
                    handleAuthError(task.exception)
                }
            }
    }

    private fun loginUser() {
        val email = binding.correo.text.toString().trim()
        val password = binding.contra.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe ingresar email y contraseña.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                    goToChat()
                } else {
                    handleAuthError(task.exception)
                }
            }
    }

    /**
     * Maneja las excepciones comunes de Firebase Authentication.
     */
    private fun handleAuthError(e: Exception?) {
        val message = when (e) {
            is FirebaseAuthWeakPasswordException -> "Contraseña débil. Debe tener al menos 6 caracteres."
            is FirebaseAuthInvalidCredentialsException -> "Credenciales inválidas. Revise el email y la contraseña."
            is FirebaseAuthUserCollisionException -> "El email ya está registrado."
            else -> "Error de autenticación: ${e?.message}"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Inicia la ChatActivity y cierra la LoginActivity.
     */
    private fun goToChat() {
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
        finish() // Evita que el usuario regrese a la pantalla de login con el botón 'atrás'
    }
}