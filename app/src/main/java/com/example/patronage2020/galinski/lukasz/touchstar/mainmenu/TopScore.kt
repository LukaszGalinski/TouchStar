package com.example.patronage2020.galinski.lukasz.touchstar.mainmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.patronage2020.galinski.lukasz.touchstar.R
import com.example.patronage2020.galinski.lukasz.touchstar.database.CustomAdapter
import com.example.patronage2020.galinski.lukasz.touchstar.database.DatabaseHandler
import kotlinx.android.synthetic.main.top_score.*

class TopScore: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_score)
        val handler =
            DatabaseHandler(
                this
            )
        val lista = handler.fetchScore()
        val arrayAdapter =
            CustomAdapter(
                this,
                lista
            )
        score_list.adapter = arrayAdapter

        ok.setOnClickListener{
            finish()
        }
    }
}
