package com.felipesaquel.evasumiot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
class CrearNoticia : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var etTitulo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnPublicarNoticia: Button
    private lateinit var btnVolverHome: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_noticia)
        db = FirebaseFirestore.getInstance()
        etTitulo = findViewById(R.id.etTitulo)
        etContenido = findViewById(R.id.etContenido)
        btnPublicarNoticia = findViewById(R.id.btnPublicarNoticia)
        btnVolverHome = findViewById(R.id.btnVolverHome)
        btnPublicarNoticia.setOnClickListener {
            validarYPublicar()
        }
        btnVolverHome.setOnClickListener {
            finish()
        }
    }
    private fun validarYPublicar() {
        val titulo = etTitulo.text.toString().trim()
        val contenido = etContenido.text.toString().trim()
        if (titulo.isEmpty() || contenido.isEmpty()) {
            Toast.makeText(this, "Debe ingresar título y contenido de la noticia.", Toast.LENGTH_SHORT).show()
            return
        }
        val noticia = hashMapOf(
            "titulo" to titulo,
            "contenido" to contenido,
            "fecha" to System.currentTimeMillis(),
            "autor" to "Admin o Usuario Actual" //
        )
        db.collection("Noticias")
            .add(noticia)
            .addOnSuccessListener {
                Toast.makeText(this, "Noticia publicada con éxito.", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al publicar: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}