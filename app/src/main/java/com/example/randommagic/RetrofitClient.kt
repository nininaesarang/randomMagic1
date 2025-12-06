package com.example.randommagic

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    private const val BASE_URL = "https://randomuser.me/"

    // 2. Interceptor para ver las llamadas en el Logcat (¡muy útil!)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // 3. La instancia de Retrofit configurada (se crea una sola vez)
    val instance: PerfilApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // El traductor de JSON
            .client(client) // El mensajero con el inspector
            .build()
        retrofit.create(PerfilApiService::class.java)
    }
}

interface PerfilApiService {
    @GET("api/") // La parte final de la URL para obtener un usuario
    suspend fun obtenerUsuarioAleatorio(): PerfilModel
}