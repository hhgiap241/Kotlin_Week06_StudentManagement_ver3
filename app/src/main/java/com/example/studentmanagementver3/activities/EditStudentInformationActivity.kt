package com.example.studentmanagementver3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.studentmanagementver3.databases.StudentDatabase
import com.example.studentmanagementver3.models.Student
import com.example.studentmanagementver3.models.StudentList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import studentmanagementver3.R
import java.io.OutputStreamWriter

class EditStudentInformationActivity : AppCompatActivity() {
    private var editStudentNameET: EditText? = null
    private var editStudentBrithdayET: EditText? = null
    private var editStudentClassSpinner: Spinner? = null
    private var radioGroup: RadioGroup? = null
    private var radioButton: RadioButton? = null
    private var saveBtn: Button? = null
    private var deleteBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student_information)

        var classroom: String? = null

        editStudentNameET = findViewById(R.id.editNameET)
        editStudentBrithdayET = findViewById(R.id.editDateET)
        editStudentClassSpinner = findViewById(R.id.editClassSpinner)
        radioGroup = findViewById(R.id.radioGroup_2)
        saveBtn = findViewById(R.id.saveAddStudentBtn_2)
        deleteBtn = findViewById(R.id.deleteStudentBtn)


        val classroomList = listOf("19KTPM1", "19KTPM2", "19KTPM3")
        val db = StudentDatabase.getInstance(this)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classroomList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editStudentClassSpinner!!.adapter = adapter
        editStudentClassSpinner!!.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    classroom = classroomList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        val intent = intent
        val position = intent.getIntExtra("position", 0)
        var oldStudent = StudentList.getStudentAtPosition(position)
        this.setValue(
            oldStudent.name,
            oldStudent.birthday,
            oldStudent.classroom,
            oldStudent.gender
        )

        saveBtn!!.setOnClickListener {
            // check empty name
            if (TextUtils.isEmpty(editStudentNameET!!.text.toString())) { // check if input is empty
                editStudentNameET!!.setError("This field can't be empty")
                return@setOnClickListener
            }
            // check empty birthday
            if (TextUtils.isEmpty(editStudentBrithdayET!!.text.toString())) { // check if input is empty
                editStudentBrithdayET!!.setError("This field can't be empty")
                return@setOnClickListener
            }

            // get student gender
            var radioID: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(radioID)

            var newStudent = Student(
                editStudentNameET!!.text.toString(),
                editStudentBrithdayET!!.text.toString(),
                classroom.toString(),
                radioButton!!.text.toString()
            )
            StudentList.editStudentInfo(oldStudent, newStudent)
            oldStudent.id = 1
            db!!.studentDao().updateStudent(newStudent)
            Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
        deleteBtn!!.setOnClickListener {
            StudentList.deleteStudent(oldStudent)
            db!!.studentDao().deleteStudent(position)
            Toast.makeText(this, "Delete successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun setValue(name: String, birthday: String, classroom: String, gender: String) {
        editStudentNameET!!.setText(name)
        editStudentBrithdayET!!.setText(birthday)
        var pos: Int = classroom.last().digitToInt() - 1
        editStudentClassSpinner!!.setSelection(pos)
        if (gender == "Male")
            radioGroup!!.check(R.id.maleRB_2)
        else if (gender == "Female")
            radioGroup!!.check(R.id.femaleRB_2)
        else
            radioGroup!!.check(R.id.otherGenderRB_2)
    }

    fun saveToFile() {
        try {
            val fileName = "studentList.json"
            // File will be in "/data/data/$packageName/files/"
            val format = Json { explicitNulls = false }
            val jsonString = format.encodeToString(StudentList.getStudentList())
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            out.write(jsonString)
            out.close()
        } catch (t: Throwable) {
            Log.e("error", t.message.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("ok", "Saved")
        this.saveToFile()
    }
}