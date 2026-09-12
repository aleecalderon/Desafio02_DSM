package com.example.viajemosv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viajemosv.databinding.ItemDestinoBinding
import java.io.File

class DestinoAdapter(
    private val destinos: MutableList<Destino>,
    private val onEditar: (Destino) -> Unit,
    private val onEliminar: (Destino) -> Unit
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
        holder.binding.tvPaisDestino.text = destino.pais
        holder.binding.tvPrecioDestino.text = "$${destino.precio}"
        holder.binding.tvDescripcionDestino.text = destino.descripcion

        Glide.with(holder.binding.ivDestino.context)
            .load(File(destino.imagenUri))
            .into(holder.binding.ivDestino)

        holder.binding.btnEditarDestino.setOnClickListener {
            onEditar(destino)
        }

        holder.binding.btnEliminarDestino.setOnClickListener {
            onEliminar(destino)
        }
    }

    override fun getItemCount(): Int {
        return destinos.size
    }
}