package com.example.pruebatecnicasupervisa.presentation.viewModel.pokemonViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicasupervisa.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository : PokemonRepository
): ViewModel() {
    val states = MutableStateFlow(PokemonStates())

    init {
        states.update {
            it.copy(
                progressIndicator = true
            )
        }
        viewModelScope.launch {
            pokemonRepository.getPokemon().onSuccess { pokemonList ->
                states.update {
                    it.copy(
                        pokemonList = pokemonList,
                        progressIndicator = false
                    )
                }
                Log.d("Pokemon", "respuesta ${pokemonList}")
            }.onFailure {
                Log.d("Pokemon", "error ${it.message}")
            }
        }
    }

    fun onEvent(events: PokemonEvents){
        when(events){

        }
    }


}