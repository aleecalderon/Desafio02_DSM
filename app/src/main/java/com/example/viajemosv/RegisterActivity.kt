package com.example.viajemosv

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.viajemosv.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnCrearCuenta.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {

        val correo = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString().trim()
        val confirmarContrasena =
            binding.etConfirmarContrasena.text.toString().trim()

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

        if (confirmarContrasena.isEmpty()) {
            binding.etConfirmarContrasena.error =
                getString(R.string.campo_obligatorio)
            binding.etConfirmarContrasena.requestFocus()
            return
        }

        if (contrasena != confirmarContrasena) {
            binding.etConfirmarContrasena.error =
                getString(R.string.contrasenas_no_coinciden)
            binding.etConfirmarContrasena.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        "Cuenta creada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this,
                        task.exception?.message
                            ?: "No se pudo crear la cuenta",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}