package com.example.randommagic

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast // Ya tienes esta importación, ¡perfecto!
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randommagic.databinding.ItemProductosBinding
import java.util.Locale

class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    // --- ¡LA MAGIA SUCEDE AQUÍ, DENTRO DEL VIEWHOLDER! ---
    inner class ProductoViewHolder(val binding: ItemProductosBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {

                    // Creamos y mostramos el Toast.
                    Toast.makeText(
                        binding.root.context, // Obtenemos el contexto desde la vista raíz del binding.
                        "Esta función aún no está implementada.", // Tu mensaje personalizado.
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }
    override fun getItemCount(): Int = productos.size

    // Y este tampoco cambia.
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.binding.apply {
            // idproducto.text = producto.id.toString() // Comentado si no tienes este TextView
            nombreproducto.text = producto.titulo
            precio.text = String.format(Locale.US, "$%.2f", producto.precio)
            descripcion.text = producto.descripcion
            categoria.text = producto.categoria

            Glide.with(holder.itemView.context)
                .load(producto.imagenUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(fotoproducto)
        }
    }
}
