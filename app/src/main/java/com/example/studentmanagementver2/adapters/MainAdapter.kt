package com.example.studentmanagementver2.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import studentmanagementver2.R

class MainAdapter(
    private val context: Activity,
    private val titles: List<String>,
    private val icons: List<Int>
) : ArrayAdapter<String>(context, R.layout.menu_list_item, titles) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView: View = inflater.inflate(R.layout.menu_list_item, null, false)
        val titleText = rowView.findViewById(R.id.studentFullNameTV) as TextView
        val imageView: ImageView = rowView.findViewById(R.id.studentAvatarIV) as ImageView

        titleText.text = titles[position]
        imageView.setImageResource(icons[position])
        return rowView
    }
}