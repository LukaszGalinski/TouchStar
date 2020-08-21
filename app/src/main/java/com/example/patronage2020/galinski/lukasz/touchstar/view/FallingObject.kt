package com.example.patronage2020.galinski.lukasz.touchstar.view

import android.content.Context
import android.content.SharedPreferences
import com.example.patronage2020.galinski.lukasz.touchstar.R

private const val SHARED_SYMBOLS = "symbols"
private const val SHARED_DEFAULT_SYMBOL_LABEL = "defaultSymbol"
private const val STAR_LABEL = "starSymbol"
private const val HEART_LABEL = "heartSymbol"
private const val FLOWER_LABEL = "flowerSymbol"

fun changeDefaultSymbol(context: Context, currentSign: String){
    val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = shared.edit()
    editor.putString(SHARED_DEFAULT_SYMBOL_LABEL, currentSign)
    editor.apply()
}

fun getDefaultSymbol(context: Context): Int {
    val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
    val sign = shared.getString(SHARED_DEFAULT_SYMBOL_LABEL, "")
    return changeImageDependsOnLoadedData(
        sign
    )
}

fun changeImageDependsOnLoadedData(sign: String?): Int {
    return when (sign){
        STAR_LABEL -> R.drawable.ic_star_black_24dp
        HEART_LABEL -> R.drawable.ic_favorite_black_24dp
        FLOWER_LABEL -> R.drawable.ic_filter_vintage_black_24dp
        else -> R.drawable.ic_star_black_24dp
    }
}