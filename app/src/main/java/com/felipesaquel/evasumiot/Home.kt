package com.felipesaquel.evasumiot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class Home : AppCompatActivity(), NoticiaAdapter.OnItemClickListener {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private lateinit var noticiaAdapter: NoticiaAdapter
    private val listaNoticias = mutableListOf<Noticia>()
    private val COLECCION_NOTICIAS = "Noticias"

    private lateinit var btnVolverLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fabCrearNoticia = findViewById<FloatingActionButton>(R.id.fabCrearNoticia)
        fabCrearNoticia.setOnClickListener {
            val intent = Intent(this, CrearNoticia::class.java)
            startActivity(intent)
        }

        btnVolverLogin = findViewById(R.id.btnVolverLogin)
        btnVolverLogin.setOnClickListener {
            cerrarSesionYVolverLogin()
        }

        recyclerView = findViewById(R.id.recyclerViewNoticias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noticiaAdapter = NoticiaAdapter(listaNoticias, this, this)
        recyclerView.adapter = noticiaAdapter

        cargarNoticias()
    }

    private fun cerrarSesionYVolverLogin() {

        val intent = Intent(this, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onItemClick(noticia: Noticia) {
        val intent = Intent(this, DetalleNoticia::class.java).apply {
            putExtra("noticia_seleccionada", noticia)
        }
        startActivity(intent)
    }

    private fun cargarNoticias() {
        Log.d("FIREBASE", "Iniciando carga de noticias de la colección: $COLECCION_NOTICIAS")
        db.collection(COLECCION_NOTICIAS)
            .orderBy("fecha", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("FIREBASE", "Error al cargar noticias: ", e)
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    listaNoticias.clear()
                    for (document in snapshots.documents) {
                        try {
                            val noticia = document.toObject(Noticia::class.java)
                            if (noticia != null) {
                                listaNoticias.add(noticia)
                            }
                        } catch (ex: Exception) {
                            Log.e("FIREBASE", "Error de mapeo para documento ${document.id}: ${ex.message}")
                        }
                    }
                    noticiaAdapter.notifyDataSetChanged()
                    Log.d("FIREBASE", "Carga finalizada. Total de noticias visibles: ${listaNoticias.size}")
                }
            }
    }
}