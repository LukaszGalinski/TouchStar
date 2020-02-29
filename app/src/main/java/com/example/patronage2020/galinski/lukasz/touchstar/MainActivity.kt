package com.example.patronage2020.galinski.lukasz.touchstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun menuOptionsListener(v: View) {
        when (v.id) {
            R.id.newgame -> startActivity(Intent(this, NewGame::class.java))
            R.id.topScore -> println("")
            R.id.options -> startActivity(Intent(this, Options::class.java))
            R.id.instruction -> startActivity(Intent(this, Instructions::class.java))
            R.id.exit -> finish()
        }
    }
}
