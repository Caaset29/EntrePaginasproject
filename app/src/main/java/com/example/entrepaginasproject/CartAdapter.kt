package com.example.entrepaginasproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.entrepaginasproject.databinding.ItemCartBookBinding

class CartAdapter(
    private var items: List<CartItem>,
    private val onQuantityChange: () -> Unit // Callback para avisar que cambió el total
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val BASE_URL_IMG = "http://192.168.1.58/"

    inner class CartViewHolder(val binding: ItemCartBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        val libro = item.libro

        holder.binding.tvCartTitle.text = libro.titulo
        holder.binding.tvCartAuthor.text = libro.autor
        holder.binding.tvCartPrice.text = "S/ ${String.format("%.2f", libro.precio)}"
        holder.binding.tvQuantity.text = item.cantidad.toString()

        if (!libro.imagen_url.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(BASE_URL_IMG + libro.imagen_url)
                .into(holder.binding.imgCartBook)
        }

        // Lógica de botones
        holder.binding.btnPlus.setOnClickListener {
            // Verificar stock real antes de aumentar (opcional)
            CartRepository.updateQuantity(libro, item.cantidad + 1)
            notifyItemChanged(position)
            onQuantityChange()
        }

        holder.binding.btnMinus.setOnClickListener {
            if (item.cantidad > 1) {
                CartRepository.updateQuantity(libro, item.cantidad - 1)
                notifyItemChanged(position)
            }
            onQuantityChange()
        }

        holder.binding.btnDelete.setOnClickListener {
            CartRepository.removeItem(libro)
            // Actualizar lista visualmente
            items = CartRepository.getCartItems()
            notifyDataSetChanged()
            onQuantityChange()
        }
    }

    override fun getItemCount(): Int = items.size
}