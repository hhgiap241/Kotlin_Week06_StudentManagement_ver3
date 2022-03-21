package com.example.studentmanagementver2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import studentmanagementver2.R
import com.example.studentmanagementver2.adapters.MainAdapter
import com.example.studentmanagementver2.models.Student
import com.example.studentmanagementver2.models.StudentList
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.FileNotFoundException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private var titles: List<String> = emptyList()
    private var icons: List<Int> = emptyList()

    fun createData() {
        titles = listOf("Input student’s information", "Display list of students")
        icons = listOf(R.drawable.add_student, R.drawable.show_student_list)
    }

    fun readJSONFile() {
        try {
            var data: ArrayList<Student> = ArrayList()
            val inputStream: InputStream = openFileInput("studentList.json")
            data = Json { ignoreUnknownKeys = true; explicitNulls = false }.decodeFromStream(
                inputStream
            )
            // set data for student list
            StudentList.setListData(data)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } catch (t: Throwable) {
            t.printStackTrace()
//            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    var mainMenuListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readJSONFile()

        mainMenuListView = findViewById(R.id.mainMenuListView)

        createData()
        val adapter = MainAdapter(this, titles, icons)
        mainMenuListView!!.adapter = adapter
        mainMenuListView!!.setOnItemClickListener { adapterView, view, i, l ->
            if (i === 0) {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
            }
            if (i === 1) {
                val intent = Intent(this, ShowStudentListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}