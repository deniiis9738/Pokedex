package com.example.pokedex.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.pokedex.ui.theme.atkColor
import com.example.pokedex.ui.theme.defColor
import com.example.pokedex.ui.theme.hpColor
import com.example.pokedex.ui.theme.sAtkColor
import com.example.pokedex.ui.theme.sDefColor
import com.example.pokedex.ui.theme.spdColor

enum class StatsColor(val color: Color) {
    HP(hpColor),
    ATTACK(atkColor),
    DEFENSE(defColor),
    SPECIAL_ATTACK(sAtkColor),
    SPECIAL_DEFENSE(sDefColor),
    SPEED(spdColor)
}