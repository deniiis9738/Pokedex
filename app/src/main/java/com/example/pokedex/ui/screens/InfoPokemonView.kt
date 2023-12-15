package com.example.pokedex.ui.screens

import com.example.pokedex.data.models.Pokemon
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.viewmodels.InfoPokemonViewModel
import com.example.pokedex.ui.theme.BlackGrey
import java.util.Locale

@Composable
fun InfoPokemonView(infoPokemonViewModel: InfoPokemonViewModel, pokemonName: String, onBackClicked: () -> Unit) {
    infoPokemonViewModel.getPokemon(pokemonName)
    val pokemon by infoPokemonViewModel.pokemon.observeAsState()
    val types = pokemon?.types ?: emptyList()

    val context = LocalContext.current
    val activity = context as? ComponentActivity
    activity?.let {
        it.window.statusBarColor =
            pokemon?.let { infoPokemonViewModel.getTypeColor(types[0].type.name)!!.toArgb() }
                ?: Color.Red.toArgb()
    }

    if (pokemon != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlackGrey),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            pokemon?.let { TopBar(it, infoPokemonViewModel, onBackClicked) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                    .background(infoPokemonViewModel.getTypeColor(types[0].type.name)!!),
                contentAlignment = Alignment.Center
            ) {
                pokemon?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = it.sprites.other.officialArtwork.frontDefault),
                        contentDescription = it.id,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            pokemon?.let {
                Text(
                    text = it.name,
                    fontSize = 40.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                itemsIndexed(types) { index, type ->
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(30.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 50.dp,
                                    bottomEnd = 50.dp,
                                    topStart = 50.dp,
                                    topEnd = 50.dp
                                )
                            )
                            .background(infoPokemonViewModel.getTypeColor(type.type.name)!!),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = type.type.name,
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                    if (index < types.size - 1) {
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (pokemon?.weight?.div(10)).toString() + " KG",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Weight",
                        fontSize = 15.sp,
                        color = Color.Gray,

                        )
                }
                Spacer(modifier = Modifier.width(100.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (pokemon?.height?.div(10)).toString() + " M",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Height",
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Base Stats",
                fontSize = 20.sp,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                val stats = pokemon?.stats ?: emptyList()

                items(stats) { stat ->
                    val statNameText = when (stat.stat.name) {
                        "attack" -> "ATK"
                        "special-attack" -> "S.ATK"
                        "defense" -> "DEF"
                        "special-defense" -> "S.DEF"
                        "speed" -> "SPD"
                        else -> stat.stat.name
                    }
                    Row(
                        modifier = Modifier.padding(3.dp)
                    ) {
                        Row(
                            modifier = Modifier.width(70.dp)
                        ) {
                            Text(
                                text = statNameText.run { uppercase(Locale.ROOT) },
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(end = 15.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier
                                .width(225.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .width((stat.baseStat * 225 / 255).dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(infoPokemonViewModel.getStatsColor(stat.stat.name)!!),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stat.baseStat.toString() + "/255",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(pokemon: Pokemon, infoPokemonViewModel: InfoPokemonViewModel, onBackClicked: () -> Unit) {
    val types = pokemon.types
    TopAppBar(
        title = { Text(
            text = "Pokedex",
            color = Color.White,
        ) },
        modifier = Modifier
            .fillMaxWidth(),
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = Color.White
                )
            }
        },
        colors = topAppBarColors(infoPokemonViewModel.getTypeColor(types[0].type.name)!!),
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "#${pokemon.id}",
                    color = Color.White
                )
            }
        }
    )
}