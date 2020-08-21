package com.example.patronage2020.galinski.lukasz.touchstar.view

import android.widget.*

private const val SCORE_PER_STAR = 15

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

fun nameValidation(name: String): Boolean {
    return name.length in 1..8
}

