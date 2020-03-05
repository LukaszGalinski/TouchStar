package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.Animator
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

private const val GAME_START_CHARACTER_SHOWTIME = 1000L
private const val SHARED_SYMBOLS = "symbols"
private const val SHARED_DEFAULT_SYMBOL_LABEL = "defaultSymbol"
private const val SCORE_PER_STAR = 15
private val handlerString = Handler()
private val textToShow = arrayOf("3", "2", "1", "START")
private val mAnimatorList: MutableList<Animator> = ArrayList()
private val starList: MutableList<ImageButton> = ArrayList()
val startTextCountdown = GAME_START_CHARACTER_SHOWTIME*textToShow.size

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
    pauseAnimations()
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

fun checkLivesLeft(currentLivesAmount: Int, livesLeft: TextView): Boolean {
    return if (currentLivesAmount > 0) {
        livesLeft.text = (currentLivesAmount-1).toString()
        true
    } else false
}

fun addScore(currentScore: Int, scoreTextView: TextView): Int {
    var newScore = currentScore
    newScore += SCORE_PER_STAR
    scoreTextView.text = newScore.toString()
    return newScore
}

fun cancelAnimations(){
    for (i in mAnimatorList){
        i.cancel()
    }
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
fun addAnimation(animation: Animator, star: ImageButton){
    mAnimatorList.add(animation)
    starList.add(star)
}

fun destroyAnimations(){
    for (i in mAnimatorList.indices){
        mAnimatorList[i].pause()
        starList[i].isClickable = false
        starList[i].visibility = View.GONE
        mAnimatorList[i].removeAllListeners()
    }
}
fun enterNameDialog(context: Context, currentScore: Int, currentStage: Int){
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(R.string.enter_your_name)

        val input = EditText(context)
        dialogBuilder.setView(input)

        dialogBuilder.setPositiveButton(R.string.save_score) { dialog, _ ->
            val userName = input.text.toString()
            if (nameValidation(userName)) {
                val data = DatabaseData(userName, currentScore.toLong(), currentStage)
                DatabaseHandler(context).insertScore(data)
                dialog.cancel()
            } else {
                Toast.makeText(context, context.resources.getText(R.string.username_rule), Toast.LENGTH_SHORT).show()
            }
        }
        val showDialog = dialogBuilder.create()
        showDialog.show()
    }

fun nameValidation(name: String): Boolean {
    return name.length in 1..8
}

