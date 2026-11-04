package uth.praktikum.praktikumpapb.data.repository

import uth.praktikum.praktikumpapb.data.model.Student
import uth.praktikum.praktikumpapb.data.source.StudentLocalDataSource

interface StudentRepository {
    fun getStudents(): List<Student>
    fun getStudentById(id: Int): Student?
}

class DefaultStudentRepository(
    private val dataSource: StudentLocalDataSource
) : StudentRepository {
    override fun getStudents(): List<Student> =
        dataSource.getStudents()

    override fun getStudentById(id: Int): Student? =
        dataSource.getStudentById(id)
}