package com.example.pruebatecnicasupervisa.data.remote.model

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)