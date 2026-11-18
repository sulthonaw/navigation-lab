package uth.praktikum.praktikumpapb.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uth.praktikum.praktikumpapb.data.repository.FavoriteRepository

class FavoritesViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteRepository.observeFavorites().collect { list ->
                _uiState.value = FavoritesUiState(
                    favorites = list,
                    isLoading = false
                )
            }
        }
    }

    companion object {
        fun factory(favoriteRepository: FavoriteRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(favoriteRepository) as T
                }
            }
    }
}
