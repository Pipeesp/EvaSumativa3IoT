package com.felipesaquel.evasumiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

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
            iniciarSesionFirebase(usuario, contrasena)
        }
    }


    private fun iniciarSesionFirebase(email: String, contrasena: String) {
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mostrarAlerta("Inicio de Sesión Exitoso", "Acceso concedido. Iniciando Dashboard IoT.")
                } else {
                    val error = task.exception?.localizedMessage ?: "Ingrese sus datos de manera correcta."
                    mostrarAlerta("Error de Autenticación", "Ingrese sus datos de manera correcta")
                }
            }
    }

    private fun verificarEstadoBluetooth() {
        val bluetoothAdapter: BluetoothAdapter? = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
        var titulo: String
        var mensaje: String

        if (bluetoothAdapter == null) {
            titulo = "Verificación Bluetooth"
            mensaje = "Este dispositivo no soporta Bluetooth."
        } else if (bluetoothAdapter.isEnabled) {
            titulo = "Verificación Bluetooth"
            mensaje = "El Bluetooth en este dispositivo esta: ENCENDIDO."
        } else {
            titulo = "Verificación Bluetooth"
            mensaje = "El Bluetooth en este dispositivo esta: APAGADO."
        }
        mostrarAlerta(titulo, mensaje)
    }


    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        if (titulo == "Inicio de Sesión Exitoso") {
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