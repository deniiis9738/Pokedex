package com.example.pokedex.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.domain.models.PokemonModel

@Composable
fun CardListPokemon(onPokemonSelected: (String) -> Unit, pokemonModel: PokemonModel?) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable {
                if (pokemonModel != null) {
                    onPokemonSelected(pokemonModel.name)
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                text = "#" + pokemonModel?.id,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(15.dp))
            AsyncImage(
                model =
                if (pokemonModel?.spritesModel?.otherModel?.officialArtworkModel?.frontDefault.isNullOrEmpty()) R.drawable.pokeball
                else pokemonModel?.spritesModel?.otherModel?.officialArtworkModel?.frontDefault,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            if (pokemonModel != null) {
                Text(
                    text = pokemonModel.name,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}