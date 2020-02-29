package com.example.patronage2020.galinski.lukasz.touchstar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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