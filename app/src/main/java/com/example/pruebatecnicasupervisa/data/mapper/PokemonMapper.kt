package com.example.pruebatecnicasupervisa.data.mapper

import com.example.pruebatecnicasupervisa.data.remote.model.PokemonResponse
import com.example.pruebatecnicasupervisa.domain.model.Pokemon

fun PokemonResponse.toDomain(): Pokemon{
    return Pokemon(
        name = name,
        img = sprites.back_default
    )
}