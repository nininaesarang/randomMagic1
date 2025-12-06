package com.example.randommagic

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface ProductoApiService {
    @GET("products")
    suspend fun obtenerProductos(): List<Producto>
}

object RetrofitProductosClient {
    private const val BASE_URL = "https://fakestoreapi.com/"

    val instance: ProductoApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ProductoApiService::class.java)
    }
}