package com.example.patronage2020.galinski.lukasz.touchstar.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patronage2020.galinski.lukasz.touchstar.R

class CustomAdapter(context: Context, dataList: List<DatabaseData>): BaseAdapter() {
    private val mContext: Context = context
    private val data = dataList

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].userId.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val mainRow = layoutInflater.inflate(R.layout.score_listview_row, parent, false)

        val id = mainRow.findViewById<TextView>(R.id.score_id)
        val name = mainRow.findViewById<TextView>(R.id.score_name)
        val score = mainRow.findViewById<TextView>(R.id.score_score)
        val stage = mainRow.findViewById<TextView>(R.id.score_stage)

        id.text = (position+1).toString()
        name.text = data[position].userName
        score.text = data[position].score.toString()
        stage.text = data[position].stage.toString()

        return mainRow
    }
}