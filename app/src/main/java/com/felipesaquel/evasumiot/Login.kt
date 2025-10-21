package com.felipesaquel.evasumiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText // 1. NUEVA IMPORTACIÓN
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRecuperarClave = findViewById<Button>(R.id.btnRecuperarClave)
        val btnRegistrarCuenta = findViewById<Button>(R.id.btnRegistrarCuenta)
        val btnVerificarBluetooth = findViewById<Button>(R.id.btnVerificarBluetooth)
        val etUsuarioEmail = findViewById<EditText>(R.id.editTextText)
        val etContrasena = findViewById<EditText>(R.id.editTextText2)

        btnIniciarSesion.setOnClickListener {
            validarLogin(etUsuarioEmail, etContrasena)
        }

        btnRegistrarCuenta.setOnClickListener {
            val intent = Intent(this, RegistrarCuenta::class.java)
            startActivity(intent)
        }

        btnRecuperarClave.setOnClickListener {
            val intent = Intent(this, RecuperarClave::class.java)
            startActivity(intent)
        }

        btnVerificarBluetooth.setOnClickListener {
            verificarEstadoBluetooth()
        }
    }

    private fun validarLogin(etUsuario: EditText, etContrasena: EditText) {
        val usuario = etUsuario.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error de Validación", "Debe ingresar Usuario/Email y Contraseña.")
        } else {
            mostrarAlerta(
                "Inicio de Sesión",
                "El inicio de sesión fue correcto."
            )
        }
    }

    private fun verificarEstadoBluetooth() {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        var titulo: String
        var mensaje: String

        if (bluetoothAdapter == null) {
            titulo = "Verficación Bluetooth"
            mensaje = "Este dispositivo no soporta Bluetooth."
        } else if (bluetoothAdapter.isEnabled) {
            titulo = "Verificación Bluetooth"
            mensaje = "El Bluetooth en este dispositivo esta: ENCENDIDO."
        } else {
            titulo = "Verficiación Bluetooth"
            mensaje = "El Bluetooth en este dispositivo esta: APAGADO."
        }
        mostrarAlerta(titulo, mensaje)
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        if (titulo == "Inicio de Sesión") {
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this, Home::class.java)
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