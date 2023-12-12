package com.example.pokedex.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.viewmodels.InfoPokemonViewModel
import com.example.pokedex.viewmodels.PokemonListViewModel

class PokemonListView {
    @Composable
    fun PokemonList(pokemonListViewModel: PokemonListViewModel, infoPokemonViewModel: InfoPokemonViewModel) {
        val pokemonList by pokemonListViewModel.pokemonList.observeAsState()
        val actualPokemon by infoPokemonViewModel.pokemon.observeAsState()

        if (pokemonList != null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(pokemonList!!.results) { pokemon ->
                    infoPokemonViewModel.getPokemon(pokemon.name)
                    Card(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(model = actualPokemon?.sprites?.other?.officialArtwork?.frontDefault),
//                            contentDescription = actualPokemon?.id,
//                            contentScale = ContentScale.Crop,
//                        )
//                        Text(text = actualPokemon?.name.toString())
                        Text(text = actualPokemon?.name.toString())
                    }
                }
            }
        }
    }
}