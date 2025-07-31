package com.example.pruebatecnicasupervisa.data.remote.reposiroty

import com.example.pruebatecnicasupervisa.data.remote.PokemonApi
import com.example.pruebatecnicasupervisa.data.remote.model.PokemonResponse
import com.example.pruebatecnicasupervisa.domain.model.Pokemon
import com.example.pruebatecnicasupervisa.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val api: PokemonApi
): PokemonRepository {
    override suspend fun getPokemon(): Result<List<Pokemon>>{
        return try {
            val pokemonDetails = mutableListOf<Pokemon>()
            val pokemonList = api.getPokemonList().results
            pokemonList.forEach {
                val detail = api.getPokemon(it.name)
                    pokemonDetails.add(Pokemon(
                        name = detail.name,
                        img = detail.sprites.front_default
                    ))
            }
            Result.success(pokemonDetails)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}