package com.example.viajemosv

import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viajemosv.databinding.ActivityCatalogoBinding
import com.google.firebase.firestore.FirebaseFirestore

class CatalogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogoBinding
    private lateinit var db: FirebaseFirestore
    private val destinos = mutableListOf<Destino>()
    private lateinit var adapter: DestinoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatalogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        configurarRecyclerView()
        cargarDestinos()

        binding.btnAgregarDestino.setOnClickListener {
            val intent = Intent(this, AgregarDestinoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        cargarDestinos()
    }

    private fun configurarRecyclerView() {

        adapter = DestinoAdapter(
            destinos,
            onEditar = { destino ->
                val intent = Intent(this, AgregarDestinoActivity::class.java)

                intent.putExtra("modo_editar", true)
                intent.putExtra("destino_id", destino.id)
                intent.putExtra("destino_nombre", destino.nombre)
                intent.putExtra("destino_pais", destino.pais)
                intent.putExtra("destino_precio", destino.precio)
                intent.putExtra("destino_descripcion", destino.descripcion)
                intent.putExtra("destino_imagen", destino.imagenUri)

                startActivity(intent)
            },
            onEliminar = { destino ->
                confirmarEliminacion(destino)
            }
        )

        binding.rvDestinos.layoutManager = LinearLayoutManager(this)
        binding.rvDestinos.adapter = adapter
    }

    private fun confirmarEliminacion(destino: Destino) {

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.eliminar))
            .setMessage(
                getString(R.string.confirmar_eliminacion)
            )
            .setPositiveButton(getString(R.string.eliminar)) { _, _ ->

                db.collection("destinos")
                    .document(destino.id)
                    .delete()
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            getString(R.string.destino_eliminado),
                            Toast.LENGTH_SHORT
                        ).show()

                        cargarDestinos()
                    }
                    .addOnFailureListener { error ->

                        Toast.makeText(
                            this,
                            error.message ?: "No se pudo eliminar el destino",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .show()
    }


    private fun cargarDestinos() {

        db.collection("destinos")
            .get()
            .addOnSuccessListener { resultado ->

                destinos.clear()

                for (documento in resultado) {

                    val destino = documento
                        .toObject(Destino::class.java)
                        .copy(id = documento.id)

                    destinos.add(destino)
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { error ->

                error.printStackTrace()
            }
    }
}