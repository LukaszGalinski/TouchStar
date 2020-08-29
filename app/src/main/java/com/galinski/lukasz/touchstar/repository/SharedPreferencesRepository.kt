package com.galinski.lukasz.touchstar.repository

import android.content.Context
import android.content.SharedPreferences
import com.galinski.lukasz.touchstar.R

private const val SHARED_SYMBOLS = "symbols"
private const val SHARED_DEFAULT_SYMBOL_LABEL = "defaultSymbol"
private const val STAR_LABEL = "starSymbol"
private const val HEART_LABEL = "heartSymbol"
private const val FLOWER_LABEL = "flowerSymbol"

class SharedPreferencesRepository {
    private var instance: SharedPreferencesRepository? = null

    fun instance(): SharedPreferencesRepository? {
        instance ?: synchronized(this){
            instance ?: SharedPreferencesRepository()
                .also { instance = it }
        }
        return instance
    }

    fun changeDefaultStarSymbol(context: Context, currentSign: String) {
        val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putString(SHARED_DEFAULT_SYMBOL_LABEL, currentSign)
        editor.apply()
    }

    fun getDefaultStarSymbol(context: Context): Int {
        val shared: SharedPreferences = context.getSharedPreferences(SHARED_SYMBOLS, Context.MODE_PRIVATE)
        val sign = shared.getString(SHARED_DEFAULT_SYMBOL_LABEL, "")
        return changeImageDependsOnLoadedData(sign)
    }

    private fun changeImageDependsOnLoadedData(sign: String?): Int {
        return when (sign) {
            STAR_LABEL -> R.drawable.sign_star
            HEART_LABEL -> R.drawable.sign_heart
            FLOWER_LABEL -> R.drawable.sign_flower
            else -> R.drawable.sign_star
        }
    }
}