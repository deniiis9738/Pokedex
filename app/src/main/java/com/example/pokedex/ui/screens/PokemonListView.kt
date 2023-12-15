package com.example.pokedex.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.ui.theme.BlackGrey
import com.example.pokedex.viewmodels.PokemonListViewModel

@Composable
fun PokemonListView(pokemonListViewModel: PokemonListViewModel, onPokemonSelected: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? ComponentActivity
    activity?.let {
        it.window.statusBarColor =
            BlackGrey.toArgb()
    }

    var isLoading by remember { mutableStateOf(false) }

    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState.layoutInfo.visibleItemsInfo) {
        val lastVisibleItemIndex = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val totalItems = pokemonListViewModel.pokemonListWithInfo.value?.size ?: 0

        if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItems - 1 && !isLoading) {
            isLoading = true
            pokemonListViewModel.onListEndReached {
                isLoading = false
            }
        }
    }
    val pokemonListWithInfo by pokemonListViewModel.pokemonListWithInfo.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGrey)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(60.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    )
                )
            }
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2)
            ) {
                val filteredPokemonList = pokemonListWithInfo.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }

                items(filteredPokemonList) { pokemon ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .clickable {
                                onPokemonSelected(pokemon.name)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(
                            width = 5.dp,
                            color = Color.Black
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
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            AsyncImage(
                                model = pokemon.sprites.other.officialArtwork.frontDefault,
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = pokemon.name,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }
}