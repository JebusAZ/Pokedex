package com.uii.pokedex

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.uii.pokedex.PokemonSummary

@Composable
fun PantallaPokedex(viewModel: PokedexViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val tiposPokemon = listOf("Normal", "Fire", "Water", "Grass", "Electric", "Ice", "Fighting", "Poison", "Ground")
    var tipoSeleccionado by remember { mutableStateOf("Normal") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(text = "Pokédex", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Barra de pestañas deslizable para los filtros
        ScrollableTabRow(selectedTabIndex = tiposPokemon.indexOf(tipoSeleccionado), edgePadding = 0.dp) {
            tiposPokemon.forEach { tipo ->
                Tab(
                    selected = tipoSeleccionado == tipo,
                    onClick = {
                        tipoSeleccionado = tipo
                        viewModel.cargarPokemonPorTipo(tipo)
                    },
                    text = { Text(tipo) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Discriminador de estados de UI
        when (val estado = uiState) {
            is PokedexUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is PokedexUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estado.pokemonList) { pokemon ->
                        TarjetaPokemon(pokemon = pokemon)
                    }
                }
            }
            is PokedexUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = estado.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun TarjetaPokemon(pokemon: PokemonSummary) {
    Card(
        modifier = Modifier.fillMaxWidth().height(180.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "#${pokemon.id.toString().padStart(4, '0')}",
                style = MaterialTheme.typography.labelMedium
            )

            // Carga asíncrona de Coil con caché inteligente incorporado
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = "Imagen de ${pokemon.name}",
                modifier = Modifier.size(90.dp).weight(1f)
            )

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}