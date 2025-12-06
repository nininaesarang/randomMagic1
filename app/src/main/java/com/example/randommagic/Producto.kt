package com.example.randommagic

import com.google.gson.annotations.SerializedName

data class Producto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val titulo: String,
    @SerializedName("price") val precio: Double,
    @SerializedName("description") val descripcion: String,
    @SerializedName("category") val categoria: String,
    @SerializedName("image") val imagenUrl: String,
    @SerializedName("rating") val calificacion: Calificacion
)

// Modelo para el objeto anidado "rating"
data class Calificacion(
    @SerializedName("rate") val estrellas: Double,
    @SerializedName("count") val conteo: Int
)

