package com.example.randommagic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.randommagic.databinding.ActivityProductosBinding
import kotlinx.coroutines.launch

class productos : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding
    private lateinit var adapter: ProductoAdapter
    private val listaDeProductos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Configurar el RecyclerView
        configurarRecyclerView()

        // 2. Obtener los datos de la API
        obtenerProductosDeAPI()
    }

    private fun configurarRecyclerView() {
        // Inicializamos el adapter con la lista (aún vacía)
        adapter = ProductoAdapter(listaDeProductos)
        binding.listaProductos.adapter = adapter
        // Opcional: Usar GridLayoutManager para mostrar 2 columnas
        binding.listaProductos.layoutManager = GridLayoutManager(this, 2)
    }

    private fun obtenerProductosDeAPI() {
        // Usar corutinas para la llamada de red
        lifecycleScope.launch {
            try {
                // Llamamos a la instancia de nuestro cliente Retrofit
                val productosObtenidos = RetrofitProductosClient.instance.obtenerProductos()

                // Limpiamos la lista actual y añadimos los nuevos productos
                listaDeProductos.clear()
                listaDeProductos.addAll(productosObtenidos)

                // Notificamos al adapter que los datos han cambiado
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                // En caso de error, mostramos un mensaje
                Toast.makeText(
                    this@productos,
                    "Error al obtener productos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
