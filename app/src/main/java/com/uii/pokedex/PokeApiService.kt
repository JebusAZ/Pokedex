package com.uii.pokedex

import com.uii.pokedex.TypeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    // Define la petición HTTP GET dinámica usando un parámetro en la URL ({typeName})
    @GET("type/{typeName}")
    suspend fun getPokemonByType(
        @Path("typeName") typeName: String
    ): TypeResponse

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        // Instancia Singleton para evitar abrir múltiples clientes HTTP en la app
        val instance: PokeApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
        }
    }
}