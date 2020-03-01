package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.Animator
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextSwitcher

const val GAME_START_CHARACTER_SHOWTIME = 1000L
private const val SHARED_SYMBOLS = "symbols"
private const val SHARED_DEFAULT_SYMBOL_LABEL = "defaultSymbol"
private val handlerString = Handler()
private val textToShow = arrayOf("3", "2", "1", "START")
val startTextLength = textToShow.size
private val mAnimatorList: MutableList<Animator> = ArrayList()

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

fun startGameCountDown(startingGameShowText: Int, context: Context, switcher: TextSwitcher) {
    var textCountDown = startingGameShowText
    switcher.setInAnimation(context, android.R.anim.slide_in_left)
    switcher.setOutAnimation(context, android.R.anim.slide_out_right)
    handlerString.postDelayed({
        if (textCountDown == textToShow.size) {
            switcher.visibility = View.GONE
        } else {
            switcher.setText(textToShow[textCountDown])
            startGameCountDown(++textCountDown, context, switcher)
        }
    }, GAME_START_CHARACTER_SHOWTIME)
}

fun cancelAnimations(){
    for (i in mAnimatorList){
        i.cancel()
    }
    mAnimatorList.clear()
}

fun pauseAnimations(){
    for (i in mAnimatorList){
        i.pause()
    }
}
fun resumeAnimations(){
    for (i in mAnimatorList){
        i.resume()
    }
}
fun addAnimation(animation: Animator){
    mAnimatorList.add(animation)
}
