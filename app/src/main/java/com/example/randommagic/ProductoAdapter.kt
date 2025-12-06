package com.example.randommagic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommagic.databinding.ItemProductosBinding
import java.util.Locale

class ProductoAdapter (private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductosBinding) : RecyclerView.ViewHolder(binding.root)

    // Crea un nuevo ViewHolder (invocado por el LayoutManager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    // Retorna el tamaño total de la lista de datos
    override fun getItemCount(): Int = productos.size

    // Asocia los datos de una posición con las vistas de un ViewHolder
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.binding.apply {
            idproducto.text = producto.id.toString()
            nombreproducto.text = producto.titulo
            precio.text = String.format(Locale.US, "$%.2f", producto.precio)
            descripcion.text = producto.descripcion
            categoria.text = producto.categoria

            Glide.with(holder.itemView.context)
                .load(producto.imagenUrl)
                .placeholder(R.drawable.ic_launcher_background) // Opcional
                .error(R.drawable.ic_launcher_foreground) // Opcional
                .into(fotoproducto)
        }
    }
}