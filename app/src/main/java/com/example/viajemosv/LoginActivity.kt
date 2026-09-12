package com.example.viajemosv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viajemosv.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnIniciarSesion.setOnClickListener {
            iniciarSesion()
        }
    }

    private fun iniciarSesion() {

        val correo = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString().trim()

        if (correo.isEmpty()) {
            binding.etCorreo.error = getString(R.string.campo_obligatorio)
            binding.etCorreo.requestFocus()
            return
        }

        if (contrasena.isEmpty()) {
            binding.etContrasena.error = getString(R.string.campo_obligatorio)
            binding.etContrasena.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        getString(R.string.inicio_sesion_exitoso),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, CatalogoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(
                        this,
                        task.exception?.message
                            ?: getString(R.string.error_iniciar_sesion),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}