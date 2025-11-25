package com.felipesaquel.evasumiot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Noticia(
    val autor: String? = null,
    val contenido: String? = null,
    val fecha: Long? = null,
    val titulo: String? = null
) : Parcelable