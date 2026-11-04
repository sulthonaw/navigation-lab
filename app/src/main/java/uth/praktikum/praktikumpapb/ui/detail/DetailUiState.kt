package uth.praktikum.praktikumpapb.ui.detail

import uth.praktikum.praktikumpapb.data.model.Student

data class DetailUiState(
    val student: Student? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)