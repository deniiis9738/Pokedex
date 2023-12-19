package com.example.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.screens.InfoPokemonView
import com.example.pokedex.ui.screens.PokemonListView
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.viewmodels.InfoPokemonViewModel
import com.example.pokedex.ui.viewmodels.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val infoPokemonViewModel: InfoPokemonViewModel by viewModels()
                    val pokemonListViewModel: PokemonListViewModel by viewModels()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "PokemonListView"
                    ) {
                        composable("PokemonListView") {
                            PokemonListView(pokemonListViewModel) { pokemonName ->
                                navController.navigate("InfoPokemonView/$pokemonName")
                            }
                        }
                        composable("InfoPokemonView/{pokemonName}") { backStackEntry ->
                            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
                            if (pokemonName != null) {
                                InfoPokemonView(infoPokemonViewModel, pokemonName) {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}