package com.example.randommagic

import com.google.gson.annotations.SerializedName

data class PerfilModel(
    @SerializedName("results") val results: List<PerfilUsuario>
)

data class PerfilUsuario(
    @SerializedName("name") val nombre: Nombre,
    @SerializedName("location") val ubicacion: Ubicacion,
    @SerializedName("dob") val fechaNacimiento: FechaNacimiento,
    @SerializedName("phone") val telefono: String,
    @SerializedName("picture") val foto: Foto
)

data class Nombre(
    @SerializedName("first") val primerNombre: String,
    @SerializedName("last") val apellido: String
)

data class Ubicacion(
    @SerializedName("street") val calle: Calle,
    @SerializedName("city") val ciudad: String,
    @SerializedName("state") val estado: String,
    @SerializedName("country") val pais: String
)

data class Calle(
    @SerializedName("number") val numero: Int,
    @SerializedName("name") val nombre: String
)

data class FechaNacimiento(
    @SerializedName("date") val fecha: String,
    @SerializedName("age") val edad: Int
)

data class Foto(
    @SerializedName("large") val fotoGrande: String
)
