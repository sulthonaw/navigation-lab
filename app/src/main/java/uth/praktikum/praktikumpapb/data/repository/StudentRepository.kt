package uth.praktikum.praktikumpapb.data.repository

import uth.praktikum.praktikumpapb.data.model.Student
import uth.praktikum.praktikumpapb.data.remote.StudentRemoteDataSource
import uth.praktikum.praktikumpapb.data.remote.dto.UserDto
import uth.praktikum.praktikumpapb.data.remote.dto.toStudent
import uth.praktikum.praktikumpapb.data.source.StudentLocalDataSource

interface StudentRepository {
    fun getStudents(callback: (Result<List<Student>>) -> Unit)
    fun getStudent(id: Int, callback: (Result<Student>) -> Unit)
    fun searchStudents(query: String, callback: (Result<List<Student>>) -> Unit)
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource
) : StudentRepository {

    override fun getStudents(callback: (Result<List<Student>>) -> Unit) {
        remoteDataSource.getStudents { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }

    override fun getStudent(id: Int, callback: (Result<Student>) -> Unit) {
        remoteDataSource.getStudent(id) { result ->
            callback(result.map { it.toStudent() })
        }
    }

    override fun searchStudents(query: String, callback: (Result<List<Student>>) -> Unit) {
        remoteDataSource.searchStudents(query) { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }
}