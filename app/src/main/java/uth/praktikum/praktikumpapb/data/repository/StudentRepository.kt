package uth.praktikum.praktikumpapb.data.repository

import uth.praktikum.praktikumpapb.data.model.Student
import uth.praktikum.praktikumpapb.data.remote.StudentRemoteDataSource
import uth.praktikum.praktikumpapb.data.remote.dto.toStudent

interface StudentRepository {
    suspend fun getStudents(): List<Student>
    suspend fun getStudent(id: Int): Student
    suspend fun searchStudents(query: String): List<Student>
}

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource
) : StudentRepository {

    override suspend fun getStudents(): List<Student> =
        remoteDataSource.getStudents().map { it.toStudent() }

    override suspend fun getStudent(id: Int): Student =
        remoteDataSource.getStudent(id).toStudent()

    override suspend fun searchStudents(query: String): List<Student> =
        remoteDataSource.searchStudents(query).map { it.toStudent() }
}
