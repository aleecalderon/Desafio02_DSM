package com.example.viajemosv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viajemosv.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding

    private val destinos = mutableListOf(
        Destino(
            nombre = "Playa El Tunco",
            pais = "El Salvador",
            precio = 150.0,
            descripcion = "Disfruta de un viaje inolvidable por las playas de El Salvador."
        ),
        Destino(
            nombre = "Antigua Guatemala",
            pais = "Guatemala",
            precio = 200.0,
            descripcion = "Descubre la historia, cultura y arquitectura de Antigua Guatemala."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {

        val adapter = DestinoAdapter(destinos)

        binding.rvDestinos.layoutManager = LinearLayoutManager(this)
        binding.rvDestinos.adapter = adapter
    }
}