package uth.praktikum.praktikumpapb.ui.home

import uth.praktikum.praktikumpapb.data.model.Student

data class HomeUiState(
    val students: List<Student> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)