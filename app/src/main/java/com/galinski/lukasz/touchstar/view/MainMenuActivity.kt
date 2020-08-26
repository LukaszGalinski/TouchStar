package com.galinski.lukasz.touchstar.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.galinski.lukasz.touchstar.R

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_layout)
    }

    fun menuOptionsListener(v: View) {
        when (v.id) {
            R.id.newgame -> startActivity(Intent(this, NewGame::class.java))
            R.id.topScore -> startActivity(Intent(this, ScoreBoardActivity::class.java))
            R.id.options -> startActivity(Intent(this, Options::class.java))
            R.id.instruction -> startActivity(Intent(this, InstructionsActivity::class.java))
            R.id.exit -> finishAffinity()
        }
    }
}