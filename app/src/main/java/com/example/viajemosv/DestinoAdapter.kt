package com.example.viajemosv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viajemosv.databinding.ItemDestinoBinding

class DestinoAdapter(
    private val destinos: MutableList<Destino>
) : RecyclerView.Adapter<DestinoAdapter.DestinoViewHolder>() {

    class DestinoViewHolder(
        val binding: ItemDestinoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DestinoViewHolder {

        val binding = ItemDestinoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DestinoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DestinoViewHolder,
        position: Int
    ) {

        val destino = destinos[position]

        holder.binding.tvNombreDestino.text = destino.nombre
        holder.binding.tvPrecioDestino.text = "$${destino.precio}"
        holder.binding.tvDescripcionDestino.text = destino.descripcion
    }

    override fun getItemCount(): Int {
        return destinos.size
    }
}