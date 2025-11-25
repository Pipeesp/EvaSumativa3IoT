package com.felipesaquel.evasumiot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoticiaAdapter(
    private val noticias: List<Noticia>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    class NoticiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textViewTituloNoticia)
        val contenido: TextView = view.findViewById(R.id.textViewContenidoNoticia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]
        holder.titulo.text = noticia.titulo ?: "Sin título"
        holder.contenido.text = noticia.contenido ?: "Sin contenido"

        holder.itemView.setOnClickListener {
            listener.onItemClick(noticia)
        }
    }

    override fun getItemCount(): Int = noticias.size

    interface OnItemClickListener {
        fun onItemClick(noticia: Noticia)
    }
}