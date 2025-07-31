package com.example.pruebatecnicasupervisa.domain.repository

import com.example.pruebatecnicasupervisa.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemon(): Result<List<Pokemon>>
}