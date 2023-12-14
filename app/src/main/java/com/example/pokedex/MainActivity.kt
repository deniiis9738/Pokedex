package com.example.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.viewmodels.PokemonListViewModel
import com.example.pokedex.ui.screens.PokemonListView
import com.example.pokedex.viewmodels.InfoPokemonViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val infoPokemonViewModel: InfoPokemonViewModel by viewModels()
//                    InfoPokemonView(infoPokemonViewModel = infoPokemonViewModel)

                    val pokemonListViewModel: PokemonListViewModel by viewModels()
                    PokemonListView(pokemonListViewModel, infoPokemonViewModel)
                }
            }
        }
    }
}