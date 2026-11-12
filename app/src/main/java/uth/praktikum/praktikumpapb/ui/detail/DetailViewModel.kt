package uth.praktikum.praktikumpapb.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uth.praktikum.praktikumpapb.data.repository.StudentRepository

class DetailViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun loadStudent(id: Int) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        repository.getStudent(id) { result ->
            result
                .onSuccess { student ->
                    uiState = DetailUiState(student = student, isLoading = false)
                }
                .onFailure { error ->
                    uiState = DetailUiState(
                        errorMessage = error.message ?: "Data tidak tersedia",
                        isLoading = false
                    )
                }
        }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(repository) as T
                }
            }
    }
}