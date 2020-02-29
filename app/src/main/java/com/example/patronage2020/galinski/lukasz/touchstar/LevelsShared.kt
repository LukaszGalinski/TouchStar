package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.Animator
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.TextSwitcher

private const val SHARED_SYMBOLS = "symbols"
private const val SHARED_DEFAULT_SYMBOL_LABEL = "defaultSymbol"

fun changeDefaultSymbol(context: Context, currentSign: String){
    val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = shared.edit()
    editor.putString(SHARED_DEFAULT_SYMBOL_LABEL, currentSign)
    editor.apply()
}

fun getDefaultSymbol(context: Context): Int {
    val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
    val sign = shared.getString(SHARED_DEFAULT_SYMBOL_LABEL, "")
    return changeImageDependsOnLoadedData(sign)
}

fun changeImageDependsOnLoadedData(sign: String?): Int {
    return when (sign){
        "starSymbol" -> R.drawable.ic_star_black_24dp
        "heartSymbol" -> R.drawable.ic_favorite_black_24dp
        "flowerSymbol" -> R.drawable.ic_filter_vintage_black_24dp
        else -> R.drawable.ic_star_black_24dp
    }
}

fun stopTheGame(animation: Animator, switcher: TextSwitcher ) {
    animation.removeAllListeners()
    animation.cancel()
    mainHandler.removeCallbacksAndMessages(null)
    switcher.visibility = View.VISIBLE
}