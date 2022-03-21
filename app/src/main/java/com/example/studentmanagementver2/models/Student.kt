package com.example.studentmanagementver2.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Student (
    var name: String,
    var birthday: String,
    var classroom: String,
    var gender: String
    )