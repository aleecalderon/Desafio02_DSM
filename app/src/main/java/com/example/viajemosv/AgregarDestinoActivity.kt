package com.example.viajemosv

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.viajemosv.databinding.ActivityAgregarDestinoBinding

class AgregarDestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarDestinoBinding
    private var imagenUri: Uri? = null
    private var imagenAnterior = ""
    private lateinit var db: FirebaseFirestore
    private var modoEditar = false
    private var destinoId = ""
    private var imagenNuevaSeleccionada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgregarDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()

        configurarSpinner()

        db = FirebaseFirestore.getInstance()

        configurarSpinner()

        modoEditar = intent.getBooleanExtra("modo_editar", false)

        if (modoEditar) {
            cargarDatosParaEditar()
        }

        binding.btnSeleccionarImagen.setOnClickListener {
            seleccionarImagen()
        }
        binding.btnGuardarDestino.setOnClickListener {
            guardarDestino()
        }
    }

    private fun cargarDatosParaEditar() {

        destinoId = intent.getStringExtra("destino_id") ?: ""

        val nombre = intent.getStringExtra("destino_nombre") ?: ""
        val pais = intent.getStringExtra("destino_pais") ?: ""
        val precio = intent.getDoubleExtra("destino_precio", 0.0)
        val descripcion = intent.getStringExtra("destino_descripcion") ?: ""
        val imagen = intent.getStringExtra("destino_imagen") ?: ""
        imagenAnterior = imagen

        binding.etNombreDestino.setText(nombre)
        binding.etPrecio.setText(precio.toString())
        binding.etDescripcion.setText(descripcion)

        val posicionPais = resources.getStringArray(R.array.lista_paises)
            .indexOf(pais)

        if (posicionPais >= 0) {
            binding.spPais.setSelection(posicionPais)
        }

        if (imagen.isNotEmpty()) {
            binding.ivImagenSeleccionada.setImageURI(
                Uri.fromFile(java.io.File(imagen))
            )
        }

        binding.tvTituloAgregar.text = getString(R.string.editar_destino)
        binding.btnGuardarDestino.text = getString(R.string.actualizar)
    }

    private fun configurarSpinner() {

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.lista_paises,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spPais.adapter = adapter
    }

    private fun seleccionarImagen() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        startActivityForResult(intent, 100)
    }

    private fun guardarImagenLocal(uri: Uri): String? {

        return try {

            val carpetaImagenes = java.io.File(filesDir, "destinos")

            if (!carpetaImagenes.exists()) {
                carpetaImagenes.mkdirs()
            }

            val nombreArchivo = "destino_${System.currentTimeMillis()}.jpg"
            val archivoDestino = java.io.File(carpetaImagenes, nombreArchivo)

            contentResolver.openInputStream(uri)?.use { inputStream ->
                archivoDestino.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            archivoDestino.absolutePath

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {

            imagenUri = data?.data

            if (imagenUri != null) {
                binding.ivImagenSeleccionada.setImageURI(imagenUri)
                imagenNuevaSeleccionada = true
            }
        }
    }

    private fun guardarDestino() {

        val nombre = binding.etNombreDestino.text.toString().trim()
        val pais = binding.spPais.selectedItem.toString()
        val precioTexto = binding.etPrecio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (nombre.isEmpty()) {
            binding.etNombreDestino.error =
                getString(R.string.campo_obligatorio)
            binding.etNombreDestino.requestFocus()
            return
        }

        if (pais == "Selecciona un país") {
            Toast.makeText(
                this,
                getString(R.string.campo_obligatorio),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (precioTexto.isEmpty()) {
            binding.etPrecio.error =
                getString(R.string.campo_obligatorio)
            binding.etPrecio.requestFocus()
            return
        }

        val precio = precioTexto.toDoubleOrNull()

        if (precio == null || precio <= 0) {
            binding.etPrecio.error =
                getString(R.string.precio_mayor_cero)
            binding.etPrecio.requestFocus()
            return
        }

        if (descripcion.isEmpty()) {
            binding.etDescripcion.error =
                getString(R.string.campo_obligatorio)
            binding.etDescripcion.requestFocus()
            return
        }

        if (descripcion.length < 20) {
            binding.etDescripcion.error =
                getString(R.string.descripcion_minima)
            binding.etDescripcion.requestFocus()
            return
        }

        if (imagenUri == null) {
            binding.ivImagenSeleccionada.setBackgroundResource(
                android.R.color.holo_red_light
            )
            return
        }

        val rutaImagen = if (modoEditar && !imagenNuevaSeleccionada) {
            imagenAnterior
        } else {
            imagenUri?.let { guardarImagenLocal(it) }
        }

        if (rutaImagen == null) {
            Toast.makeText(
                this,
                getString(R.string.imagen_obligatoria),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val destino = Destino(
            nombre = nombre,
            pais = pais,
            precio = precio,
            descripcion = descripcion,
            imagenUri = rutaImagen
        )

        if (modoEditar) {

            db.collection("destinos")
                .document(destinoId)
                .set(destino.copy(id = destinoId))
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        getString(R.string.destino_actualizado),
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
                .addOnFailureListener { error ->

                    Toast.makeText(
                        this,
                        error.message ?: "No se pudo actualizar el destino",
                        Toast.LENGTH_LONG
                    ).show()
                }

        } else {

            db.collection("destinos")
                .add(destino)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        getString(R.string.destino_guardado),
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
                .addOnFailureListener { error ->

                    Toast.makeText(
                        this,
                        error.message ?: "No se pudo guardar el destino",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

}