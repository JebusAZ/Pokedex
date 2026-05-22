package com.uii.pokedex

// Estructura que mapea la respuesta completa del filtro por tipo
data class TypeResponse(
    val pokemon: List<TypePokemonEntry>
)

// Nodo intermedio del JSON de la PokéAPI
data class TypePokemonEntry(
    val pokemon: PokemonSummary
)

// Contenedor con la información final de cada Pokémon
data class PokemonSummary(
    val name: String,
    val url: String
) {
    // Propiedad calculada: Extrae el ID numérico al cortar el final de la URL
    val id: Int
        get() {
            val limpiada = url.dropLast(1)
            return limpiada.substringAfterLast("/").toIntOrNull() ?: 0
        }

    // Genera la URL directa a los servidores de GitHub donde está el arte oficial en HD
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}