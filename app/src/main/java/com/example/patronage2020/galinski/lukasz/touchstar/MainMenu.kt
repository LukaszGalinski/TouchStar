package com.example.patronage2020.galinski.lukasz.touchstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.patronage2020.galinski.lukasz.touchstar.mainmenu.Instructions
import com.example.patronage2020.galinski.lukasz.touchstar.mainmenu.Options
import com.example.patronage2020.galinski.lukasz.touchstar.mainmenu.TopScore

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
    }

    fun menuOptionsListener(v: View) {
        when (v.id) {
            R.id.newgame -> startActivity(Intent(this, NewGame::class.java))
            R.id.topScore -> startActivity(Intent(this, TopScore::class.java))
            R.id.options -> startActivity(Intent(this, Options::class.java))
            R.id.instruction -> startActivity(Intent(this, Instructions::class.java))
            R.id.exit -> finish()
        }
    }
}