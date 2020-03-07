package com.example.patronage2020.galinski.lukasz.touchstar.mainmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.patronage2020.galinski.lukasz.touchstar.R
import kotlinx.android.synthetic.main.instructions.*

class Instructions: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.instructions)

        ok.setOnClickListener{
            finish()
        }
    }
}