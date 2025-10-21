package com.felipesaquel.evasumiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegistrarCuenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cuenta)

        val btnRegistrarCuenta = findViewById<Button>(R.id.btnRegistrarCuenta)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val etNombre = findViewById<EditText>(R.id.editTextText4)
        val etEmail = findViewById<EditText>(R.id.editTextText5)
        val etContrasena = findViewById<EditText>(R.id.editTextText6)
        val etConfirmarContrasena = findViewById<EditText>(R.id.editTextText7)

        btnRegistrarCuenta.setOnClickListener {
            validarRegistro(etNombre, etEmail, etContrasena, etConfirmarContrasena)
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun validarRegistro(
        etNombre: EditText, etEmail: EditText, etContrasena: EditText, etConfirmar: EditText
    ) {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val contrasena = etContrasena.text.toString()
        val confirmar = etConfirmar.text.toString()

        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta("Error de Validación", "Debe completar todos los campos requeridos.")
        } else if (contrasena != confirmar) {
            mostrarAlerta("Error de Validación", "Las contraseñas ingresadas no coinciden.")
        } else {
            mostrarAlerta(
                "Registro Exitoso",
                "Cuenta creada en el sistema. Ya puedes iniciar sesión."
            )
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        if (titulo == "Registro Exitoso") {
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        } else {
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}