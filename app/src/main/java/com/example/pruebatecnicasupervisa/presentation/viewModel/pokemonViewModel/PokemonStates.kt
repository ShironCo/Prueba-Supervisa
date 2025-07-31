package com.example.pruebatecnicasupervisa.presentation.viewModel.pokemonViewModel

import com.example.pruebatecnicasupervisa.domain.model.Pokemon

data class PokemonStates(
    val pokemonList: List<Pokemon> = emptyList(),
    val progressIndicator: Boolean = false
)
