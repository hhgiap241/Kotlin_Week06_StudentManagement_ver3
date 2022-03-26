package com.example.studentmanagementver3.models


object StudentList {
    private var studentList = ArrayList<Student>()
    private var studentNameList = ArrayList<String>()

    fun setListData(studentList: ArrayList<Student>) {
        this.studentList = studentList
        // set up name list for search
        studentNameList.clear()
        for (i in StudentList.studentList.indices){
            studentNameList.add(StudentList.studentList[i].name)
        }
    }

    fun getStudentNameList():ArrayList<String>{
        return this.studentNameList
    }

    fun getStudentList(): ArrayList<Student> {
        return this.studentList
    }

    fun addStudent(student: Student) {
        studentList.add(student)
        studentNameList.add(student.name)
    }

    fun deleteStudent(position: Int) {
        for (i in position + 1 until studentList.size) {
            studentList[i].id -= 1
        }
        studentList.removeAt(position)
        studentNameList.removeAt(position)

    }

    fun editStudentInfo(position: Int, newStudent: Student) {
        studentList[position].name = newStudent.name
        studentList[position].birthday = newStudent.birthday
        studentList[position].gender = newStudent.gender
        studentList[position].classroom = newStudent.classroom
        // update name
        studentNameList[position] = newStudent.name
    }

    fun getItemCount(): Int {
        return this.studentList.size
    }

    fun getStudentAtPosition(position: Int): Student {
        return this.studentList[position]
    }

}