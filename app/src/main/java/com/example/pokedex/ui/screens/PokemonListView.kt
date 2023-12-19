package com.example.pokedex.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.pokedex.ui.components.CardListPokemon
import com.example.pokedex.ui.theme.BlackGrey
import com.example.pokedex.ui.viewmodels.PokemonListViewModel
import kotlinx.coroutines.delay

@Composable
fun PokemonListView(pokemonListViewModel: PokemonListViewModel, onPokemonSelected: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? ComponentActivity
    activity?.let {
        it.window.statusBarColor =
            BlackGrey.toArgb()
    }

    val lazyGridState = rememberLazyGridState()
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(lazyGridState.layoutInfo.visibleItemsInfo) {
        val lastVisibleItemIndex = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val totalPokemons = pokemonListViewModel.pokemonListWithInfo.value?.size ?: 0

        if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalPokemons - 1 && !isLoading) {
            isLoading = true
            pokemonListViewModel.getPokemonList {
                isLoading = false
            }
        }
    }
    val pokemonListWithInfo by pokemonListViewModel.pokemonListWithInfo.observeAsState(emptyList())
    val pokemonList by pokemonListViewModel.pokemonList.observeAsState(emptyList())

    LaunchedEffect(searchText) {
        delay(200)
        pokemonListViewModel.performSearch(searchText)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                                    },
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
                if (searchText.isEmpty()) {
                    items(pokemonListWithInfo) { pokemon ->
                        CardListPokemon(onPokemonSelected, pokemon)
                    }
                } else {
                    items(pokemonList) { pokemon ->
                        CardListPokemon(onPokemonSelected, pokemon)
                    }
                }
            }
        }
    }
}