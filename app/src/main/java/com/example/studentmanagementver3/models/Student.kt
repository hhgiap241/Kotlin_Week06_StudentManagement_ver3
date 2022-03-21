package com.example.studentmanagementver3.models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class Student (
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "birthday")
    var birthday: String,
    @ColumnInfo(name = "classroom")
    var classroom: String,
    @ColumnInfo(name = "gender")
    var gender: String
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}