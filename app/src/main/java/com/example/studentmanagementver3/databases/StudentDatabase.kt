package com.example.studentmanagementver3.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentmanagementver3.interfaces.StudentDAO
import com.example.studentmanagementver3.models.Student

@Database(entities = arrayOf(Student::class), version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDAO

    companion object{
        val DATABASE_NAME = "student_database"
        private var instance: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase?{
            return instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, StudentDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
    }
}