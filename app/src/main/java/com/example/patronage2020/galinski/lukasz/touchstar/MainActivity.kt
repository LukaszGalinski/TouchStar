package com.example.patronage2020.galinski.lukasz.touchstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   //     val essa = DatabaseHandler(this)
    //    val cos = DatabaseData("Szymon", 5000, 5)
   //     val cos2 = DatabaseData("Johny", 2500, 4)
    //    essa.insertScore(cos)
     //   essa.insertScore(cos2)
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
