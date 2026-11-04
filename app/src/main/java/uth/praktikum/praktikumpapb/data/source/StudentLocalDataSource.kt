package uth.praktikum.praktikumpapb.data.source

import uth.praktikum.praktikumpapb.data.model.Student

class StudentLocalDataSource {
    private val students = listOf(
        Student(2026, "Alya Putri", "Teknik Informatika", 2026),
        Student(2027, "Bima Pratama", "Teknik Informatika", 2026),
        Student(2028, "Citra Lestari", "Teknik Informatika", 2026),
        Student(2029, "Dina Maharani", "Sistem Informasi", 2026),
        Student(2030, "Eko Prasetyo", "Teknologi Informasi", 2026)
    )

    fun getStudents(): List<Student> = students

    fun getStudentById(id: Int): Student? =
        students.find { it.id == id }
}