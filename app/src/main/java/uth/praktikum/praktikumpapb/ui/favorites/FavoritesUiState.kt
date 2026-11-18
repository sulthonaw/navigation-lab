package uth.praktikum.praktikumpapb.ui.favorites

import uth.praktikum.praktikumpapb.data.local.FavoriteStudentEntity

data class FavoritesUiState(
    val favorites: List<FavoriteStudentEntity> = emptyList(),
    val isLoading: Boolean = false
)
