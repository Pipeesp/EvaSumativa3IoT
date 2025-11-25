package com.felipesaquel.evasumiot

import android.os.Build
import android.os.Bundle
import android.widget.Button // Importar Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetalleNoticia : AppCompatActivity() {

    private lateinit var tvDetalleTitulo: TextView
    private lateinit var tvDetalleContenido: TextView
    private lateinit var tvDetalleAutorFecha: TextView
    private lateinit var btnVolverDashboard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_noticia)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarDetalle)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tvDetalleTitulo = findViewById(R.id.tvDetalleTitulo)
        tvDetalleContenido = findViewById(R.id.tvDetalleContenido)
        tvDetalleAutorFecha = findViewById(R.id.tvDetalleFecha)
        btnVolverDashboard = findViewById(R.id.btnVolverDashboard)
        btnVolverDashboard.setOnClickListener {
            finish()
        }

        val noticia: Noticia? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("noticia_seleccionada", Noticia::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("noticia_seleccionada") as? Noticia
        }

        if (noticia != null) {
            mostrarDetalleNoticia(noticia)
        } else {
            tvDetalleTitulo.text = "Error de Datos"
            tvDetalleContenido.text = "No se pudo cargar el contenido de la noticia."
            Toast.makeText(this, "Error: Objeto Noticia faltante.", Toast.LENGTH_LONG).show()
        }
    }
    private fun mostrarDetalleNoticia(noticia: Noticia) {
        val titulo = noticia.titulo ?: "Título no disponible"
        val contenido = noticia.contenido ?: "Contenido no disponible"
        val autor = noticia.autor ?: "Autor desconocido"
        val fechaFormateada = if (noticia.fecha != null) {
            val date = Date(noticia.fecha)
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date)
        } else {
            "Fecha desconocida"
        }

        tvDetalleTitulo.text = titulo
        tvDetalleContenido.text = contenido
        tvDetalleAutorFecha.text = "Publicado el $fechaFormateada"
    }
}