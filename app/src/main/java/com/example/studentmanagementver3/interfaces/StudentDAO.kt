package com.example.studentmanagementver3.interfaces

import androidx.room.*
import com.example.studentmanagementver3.models.Student

@Dao
public interface StudentDAO {

    @Query ("INSERT INTO student (id, name, birthday, classroom, gender) VALUES (:id, :name, :birthday, :classroom, :gender)")
    fun insertStudent(id: Int, name: String, birthday: String, classroom: String, gender: String)

    @Query("SELECT * FROM student")
    fun getStudentList(): List<Student>

    @Update
    fun updateStudent(student: Student)

    @Query("DELETE FROM student WHERE id = :id")
    fun deleteStudent(id: Int)

    @Query("UPDATE student SET id = id - 1 WHERE id > :id")
    fun updateID(id: Int)

}