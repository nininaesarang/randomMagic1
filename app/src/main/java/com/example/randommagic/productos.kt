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

        configurarRecyclerView()
        obtenerProductosDeAPI()
    }

    private fun configurarRecyclerView() {
        adapter = ProductoAdapter(listaDeProductos)
        binding.listaProductos.adapter = adapter
        binding.listaProductos.layoutManager = GridLayoutManager(this, 2)
    }

    private fun obtenerProductosDeAPI() {
        lifecycleScope.launch {
            try {
                val productosObtenidos = RetrofitProductosClient.instance.obtenerProductos()
                listaDeProductos.clear()
                listaDeProductos.addAll(productosObtenidos)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Toast.makeText(
                    this@productos,
                    "Error al obtener productos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
