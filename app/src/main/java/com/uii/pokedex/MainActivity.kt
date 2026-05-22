package com.uii.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.uii.pokedex.PantallaPokedex
import com.uii.pokedex.PokedexViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instanciamos el intermediario
        val pokedexViewModel = PokedexViewModel()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Invocamos la pantalla pasándole el ViewModel correspondiente
                PantallaPokedex(viewModel = pokedexViewModel)
            }
        }
    }
}