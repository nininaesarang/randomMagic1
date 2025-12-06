package com.example.randommagic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.randommagic.databinding.ItemPerfilBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class perfil : AppCompatActivity() {
    private lateinit var binding: ItemPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        obtenerDatosDelPerfil()
    }

    private fun obtenerDatosDelPerfil() {
        lifecycleScope.launch {
            try {
                val respuesta = RetrofitClient.instance.obtenerUsuarioAleatorio()
                val usuario = respuesta.results.firstOrNull()
                if (usuario != null) {
                    actualizarUI(usuario)
                } else {
                    mostrarError("No se recibieron datos del usuario.")
                }
            } catch (e: Exception) {
                mostrarError("Error al obtener el perfil: ${e.message}")
            }
        }
    }

    private fun actualizarUI(usuario: PerfilUsuario) {
        binding.nombre.text = "${usuario.nombre.primerNombre} ${usuario.nombre.apellido}"
        binding.telefono.text = usuario.telefono

        val direccionCompleta = "${usuario.ubicacion.calle.nombre} ${usuario.ubicacion.calle.numero}, ${usuario.ubicacion.ciudad}"
        binding.direccion.text = direccionCompleta

        binding.cumple.text = formatearFecha(usuario.fechaNacimiento.fecha)

        Glide.with(this)
            .load(usuario.foto.fotoGrande)
            .circleCrop() // Para que la foto sea redonda, ¡un buen toque!
            .error(R.drawable.ic_launcher_foreground) // Imagen de error
            .into(binding.fotoperfil)
    }

    private fun formatearFecha(fechaISO: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
            formatter.format(parser.parse(fechaISO)!!)
        } catch (e: Exception) {
            "Fecha no disponible"
        }
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}