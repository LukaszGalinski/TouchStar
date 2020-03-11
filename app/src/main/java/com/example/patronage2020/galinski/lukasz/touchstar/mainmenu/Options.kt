package com.example.patronage2020.galinski.lukasz.touchstar.mainmenu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.patronage2020.galinski.lukasz.touchstar.R
import com.example.patronage2020.galinski.lukasz.touchstar.changeDefaultSymbol
import kotlinx.android.synthetic.main.options.*

private const val INACTIVE_SYMBOL_ALPHA = 0.5F
private const val BASIC_SYMBOL_ALPHA = 1F
private const val BASIC_SYMBOL = "starSymbol"
private var currentSign = BASIC_SYMBOL
class Options: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options)
        ok.setOnClickListener {
            changeDefaultSymbol(
                this,
                currentSign
            )
            finish()
        }
    }

    private fun changeAlpha() {
        val symbols = arrayOf(starSymbol, heartSymbol, flowerSymbol)
        for (i in symbols.indices) {
            val currentElement = symbols[i]
            currentElement.alpha =
                INACTIVE_SYMBOL_ALPHA
        }
    }

    fun onClick(v: View) {
        changeAlpha()
        v.alpha =
            BASIC_SYMBOL_ALPHA
        currentSign = v.context.resources.getResourceEntryName(v.id)
    }
}