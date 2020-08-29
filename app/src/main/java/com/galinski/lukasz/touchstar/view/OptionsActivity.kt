package com.galinski.lukasz.touchstar.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.galinski.lukasz.touchstar.R
import com.galinski.lukasz.touchstar.viewmodel.OptionsViewModel
import kotlinx.android.synthetic.main.options_layout.*

private const val INACTIVE_SYMBOL_ALPHA = 0.5F
private const val BASIC_SYMBOL_ALPHA = 1F
private const val BASIC_SYMBOL = "starSymbol"
private var currentSign = BASIC_SYMBOL
private lateinit var optionsViewModel: OptionsViewModel

class Options: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_layout)
        optionsViewModel = ViewModelProvider(this).get(OptionsViewModel::class.java)
        optionsViewModel.instance()
        ok.setOnClickListener {
            optionsViewModel.changeDefaultStarSymbol(this, currentSign)
            finish()
        }
    }

    private fun changeAlpha() {
        val symbols = arrayOf(starSymbol, heartSymbol, flowerSymbol)
        for (i in symbols.indices) {
            val currentElement = symbols[i]
            currentElement.alpha = INACTIVE_SYMBOL_ALPHA
        }
    }

    fun onClick(v: View) {
        changeAlpha()
        v.alpha = BASIC_SYMBOL_ALPHA
        currentSign = v.context.resources.getResourceEntryName(v.id)
    }
}