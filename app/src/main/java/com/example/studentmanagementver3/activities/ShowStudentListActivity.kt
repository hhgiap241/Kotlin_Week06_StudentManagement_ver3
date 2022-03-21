package com.example.studentmanagementver3.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementver3.adapters.StudentListAdapter
import com.example.studentmanagementver3.models.Student
import com.example.studentmanagementver3.models.StudentList
import studentmanagementver3.R

class ShowStudentListActivity : AppCompatActivity() {

    private var adapter: StudentListAdapter? = null
    private var autoCompleteTVAdapter: ArrayAdapter<String>? = null
    private var studentRecyclerView: RecyclerView? = null
    private var addStudentBtn: Button? = null
    private var changeLayoutBtn: Button? = null
    private var isLinearLayoutManager: Boolean? = null
    private var autoCompleteTV: AutoCompleteTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_student_list)

        studentRecyclerView = findViewById(R.id.studentListRV)
        addStudentBtn = findViewById(R.id.addStudentBtn)
        changeLayoutBtn = findViewById(R.id.changeLayoutBtn)
        autoCompleteTV = findViewById(R.id.searchStudentTV)

        isLinearLayoutManager = this.loadState()
        // set up adapter
        setUpRecyclerAdapter(StudentList.getStudentList(), isLinearLayoutManager!!)
        if (isLinearLayoutManager as Boolean)
            studentRecyclerView!!.layoutManager = LinearLayoutManager(this)
        else
            studentRecyclerView!!.layoutManager = GridLayoutManager(this, 2)

        studentRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        studentRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )

        addStudentBtn!!.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
        changeLayoutBtn!!.setOnClickListener {
            if (isLinearLayoutManager as Boolean) {
                isLinearLayoutManager = false
                setUpRecyclerAdapter(StudentList.getStudentList(), isLinearLayoutManager!!)
                studentRecyclerView!!.layoutManager = GridLayoutManager(this, 2)

            } else {
                isLinearLayoutManager = true
                setUpRecyclerAdapter(StudentList.getStudentList(), isLinearLayoutManager!!)
                studentRecyclerView!!.layoutManager = LinearLayoutManager(this)
            }
        }

        // auto complete text view handling
        setUpAutoCompleteTVAdapter(StudentList.getStudentNameList())
    }

    fun setUpRecyclerAdapter(data: ArrayList<Student>, isLinearLayoutManager: Boolean) {
        adapter = StudentListAdapter(data, isLinearLayoutManager!!)
        studentRecyclerView!!.adapter = adapter
        adapter!!.onItemClick = { student ->
            val intent = Intent(
                this@ShowStudentListActivity,
                EditStudentInformationActivity::class.java
            )
            intent.putExtra("name", student.name)
            intent.putExtra("classroom", student.classroom)
            intent.putExtra("gender", student.gender)
            intent.putExtra("birthday", student.birthday)
            startActivity(intent)
        }
    }

    fun setUpAutoCompleteTVAdapter(data: ArrayList<String>) {
        autoCompleteTVAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        autoCompleteTV!!.setAdapter(autoCompleteTVAdapter)
        autoCompleteTV!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val foundStudents: ArrayList<Student> =
                    StudentList.getStudentList()
                        .filter { s ->
                            s.name.contains(
                                (p0.toString()),
                                true
                            )
                        } as ArrayList<Student>
                setUpRecyclerAdapter(foundStudents, isLinearLayoutManager!!)
            }
        })
    }

    fun saveState() {
        val ref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        ref.edit().putBoolean("LayoutManager", isLinearLayoutManager!!).commit()
    }

    fun loadState(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val isLinearLayoutManager = sharedPreferences.getBoolean("LayoutManager", false)
        return isLinearLayoutManager
    }

    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        Log.i("continue", "view list continue")
        val text: Editable = autoCompleteTV!!.text
        autoCompleteTV!!.setText(text)
        autoCompleteTV!!.setSelection(text.length)

        adapter!!.notifyDataSetChanged()
        setUpAutoCompleteTVAdapter(StudentList.getStudentNameList())
    }
}