package com.example.pruebatecnicasupervisa.data.remote

import com.example.pruebatecnicasupervisa.data.remote.model.PokemonListResponse
import com.example.pruebatecnicasupervisa.data.remote.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon/")
    suspend fun getPokemonList() : PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse
}