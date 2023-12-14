package com.example.pokedex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.ui.theme.BlackGrey
import com.example.pokedex.viewmodels.InfoPokemonViewModel
import com.example.pokedex.viewmodels.PokemonListViewModel

@Composable
fun PokemonListView(pokemonListViewModel: PokemonListViewModel, infoPokemonViewModel: InfoPokemonViewModel) {
    val pokemonListWithInfo by pokemonListViewModel.pokemonListWithInfo.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGrey)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(pokemonListWithInfo) { pokemon ->
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 4.dp,
                        color = Color.White
                    ),
                    shape = RoundedCornerShape(60.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "#" + pokemon.id,
                            color = Color.White
                            )
                        AsyncImage(
                            model = pokemon.sprites.other.officialArtwork.frontDefault,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = pokemon.name,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}