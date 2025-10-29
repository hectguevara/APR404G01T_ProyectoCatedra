package com.example.peacenest.ui.theme

enum class ThemeType {
    SERENE,
    FOREST,
    SUNSET,
    OCEAN
}

fun ThemeType.getDisplayName(): String {
    return when (this) {
        ThemeType.SERENE -> "Sereno 💜"
        ThemeType.FOREST -> "Bosque 🌿"
        ThemeType.SUNSET -> "Atardecer 🌅"
        ThemeType.OCEAN -> "Océano 🌊"
    }
}

fun ThemeType.getDescription(): String {
    return when (this) {
        ThemeType.SERENE -> "Calma y tranquilidad"
        ThemeType.FOREST -> "Naturaleza y renovación"
        ThemeType.SUNSET -> "Calidez y comodidad"
        ThemeType.OCEAN -> "Profundidad y serenidad"
    }
}

