package uth.praktikum.praktikumpapb.data.repository

import kotlinx.coroutines.flow.Flow
import uth.praktikum.praktikumpapb.data.local.FavoriteStudentDao
import uth.praktikum.praktikumpapb.data.local.FavoriteStudentEntity
import uth.praktikum.praktikumpapb.data.model.Student

class FavoriteRepository(private val dao: FavoriteStudentDao) {

    fun observeIsFavorite(id: Int): Flow<Boolean> =
        dao.observeIsFavorite(id)

    fun observeFavorites(): Flow<List<FavoriteStudentEntity>> =
        dao.observeFavorites()

    suspend fun add(student: Student) {
        dao.insert(
            FavoriteStudentEntity(
                id = student.id,
                firstName = student.name,
                lastName = "",
                email = student.email
            )
        )
    }

    suspend fun remove(id: Int) {
        dao.deleteById(id)
    }
}