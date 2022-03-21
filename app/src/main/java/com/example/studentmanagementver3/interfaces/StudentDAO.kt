package com.example.studentmanagementver3.interfaces

import androidx.room.*
import com.example.studentmanagementver3.models.Student

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM student")
    fun getStudentList(): List<Student>

    @Insert
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Query("DELETE FROM student WHERE id = :id")
    fun deleteStudent(id: Int)
}