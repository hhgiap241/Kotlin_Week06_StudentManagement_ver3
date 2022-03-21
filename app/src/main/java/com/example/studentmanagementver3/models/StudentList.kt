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

    //    fun setNameListData(){
//        for (i in studentList.indices){
//            studentNameList.add(studentList[i].name)
//        }
//    }
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

    fun deleteStudent(student: Student) {
        for (i in studentList.indices) {
            if (studentList[i].name == student.name &&
                studentList[i].classroom == student.classroom &&
                studentList[i].gender == student.gender &&
                studentList[i].birthday == student.birthday
            ) {
                studentList.removeAt(i)
                studentNameList.removeAt(i)
                return
            }
        }
    }

    fun editStudentInfo(oldStudent: Student, newStudent: Student) {
        for (i in studentList.indices) {
            if (studentList[i].name == oldStudent.name &&
                studentList[i].classroom == oldStudent.classroom &&
                studentList[i].gender == oldStudent.gender &&
                studentList[i].birthday == oldStudent.birthday
            ) {
                studentList[i].name = newStudent.name
                studentList[i].birthday = newStudent.birthday
                studentList[i].gender = newStudent.gender
                studentList[i].classroom = newStudent.classroom
                // update name
                studentNameList[i] = newStudent.name
                return
            }
        }
    }

    fun getItemCount(): Int {
        return this.studentList.size
    }

    fun getStudentAtPosition(position: Int): Student {
        return this.studentList[position]
    }

}