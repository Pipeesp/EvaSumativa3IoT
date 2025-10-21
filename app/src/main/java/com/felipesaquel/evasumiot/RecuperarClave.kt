package com.felipesaquel.evasumiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText // Importación necesaria
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RecuperarClave : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_clave)

        val btnRecuperarClave = findViewById<Button>(R.id.btnRecuperarClave)

        val etEmailUsuario = findViewById<EditText>(R.id.editTextText3)

        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnRecuperarClave.setOnClickListener {
            validarRecuperacion(etEmailUsuario)
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun validarRecuperacion(etEmailUsuario: EditText) {
        val emailUsuario = etEmailUsuario.text.toString().trim()

        if (emailUsuario.isEmpty()) {
            mostrarAlerta("Error de Validación", "Debe ingresar su Email o Nombre de Usuario.")
        } else {
            mostrarAlerta(
                "Recuperación de Clave",
                "Se ha enviado un correo de recuperación. Revisa tu email."
            )
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        if (titulo == "Recuperación de Clave") {
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
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