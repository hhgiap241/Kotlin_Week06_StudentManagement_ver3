package com.example.studentmanagementver3.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementver3.models.Student
import studentmanagementver3.R


class StudentListAdapter(
    private val studentList: ArrayList<Student>,
    private val isLinearLayoutManager: Boolean
) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {
    var onItemClick: ((Student) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val studentNameTV = listItemView.findViewById<TextView>(R.id.studentFullNameTV)
        val studentClassTV = listItemView.findViewById<TextView>(R.id.studentClassTV)
        val studentOtherInforTV = listItemView.findViewById<TextView>(R.id.studentOtherInfoTV)
        val studentAvatarIV = listItemView.findViewById<ImageView>(R.id.studentAvatarIV)

        init {
            listItemView.setOnClickListener {
                Log.i("hdlog2", adapterPosition.toString())
                Log.i("hdlog1", "student.id: ${studentList[adapterPosition].id}")
                onItemClick?.invoke(studentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var studentView: View? = null
        if (isLinearLayoutManager) { // linear layout
            studentView = inflater.inflate(R.layout.student_list_item, parent, false)
        } else { // grid layout
            studentView = inflater.inflate(R.layout.student_list_item_2, parent, false)
        }
        return ViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student: Student = studentList[position]
        val studentNameTextView = holder.studentNameTV
        studentNameTextView.setText(student.name)
        val studentClassTextView = holder.studentClassTV
        studentClassTextView.setText(student.classroom)
        val studentOtherInforTextView = holder.studentOtherInforTV
        studentOtherInforTextView.setText(student.birthday + " - " + student.gender)
        val studentAvatarImageView = holder.studentAvatarIV
        studentAvatarImageView.setImageResource(R.drawable.student_avatar)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}