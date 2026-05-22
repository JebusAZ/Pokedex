package com.uii.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uii.pokedex.PokeApiService
import com.uii.pokedex.PokemonSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estados seguros controlados mediante una interfaz sellada
sealed interface PokedexUiState {
    object Loading : PokedexUiState
    data class Success(val pokemonList: List<PokemonSummary>) : PokedexUiState
    data class Error(val message: String) : PokedexUiState
}

class PokedexViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PokedexUiState>(PokedexUiState.Loading)
    val uiState: StateFlow<PokedexUiState> = _uiState

    init {
        // Carga por defecto la lista de tipo normal al abrir la app
        cargarPokemonPorTipo("normal")
    }

    fun cargarPokemonPorTipo(tipo: String) {
        viewModelScope.launch {
            _uiState.value = PokedexUiState.Loading
            try {
                // Consume el servicio en segundo plano gracias a las corrutinas
                val response = PokeApiService.instance.getPokemonByType(tipo.lowercase())
                _uiState.value = PokedexUiState.Success(response.pokemon.map { it.pokemon })
            } catch (e: Exception) {
                _uiState.value = PokedexUiState.Error("Error: No se pudo conectar al servidor remoto.")
            }
        }
    }
}