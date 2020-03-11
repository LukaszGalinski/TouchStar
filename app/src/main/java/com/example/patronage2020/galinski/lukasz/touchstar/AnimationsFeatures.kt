package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.Animator
import android.view.View
import android.widget.*

private val mAnimatorList: MutableList<Animator> = ArrayList()
private val starList: MutableList<ImageButton> = ArrayList()

fun stopTheGame(animation: Animator, switcher: TextSwitcher ) {
    animation.removeAllListeners()
    pauseAnimations()
    mainHandler.removeCallbacksAndMessages(null)
    switcher.visibility = View.VISIBLE
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

fun addAnimationToList(animation: Animator, star: ImageButton){
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

