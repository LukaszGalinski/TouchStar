package com.example.patronage2020.galinski.lukasz.touchstar

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.top_score.*

class ScoreListAdapter: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_score)

        val handler = DatabaseHandler(this)
        val lista = handler.fetchData()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        score_list.adapter = arrayAdapter
    }
}
