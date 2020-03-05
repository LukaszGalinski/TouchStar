package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.level_1.*

private const val BASIC_FALLING_DURATION = 4999L
private const val BASIC_DELAY_DURATION = 500L
private const val DECREASE_STARS_SPAWN_AREA_WIDTH = 100     //Secure stars spawning in the outside of the screen
private const val DECREASE_STARS_SPAWN_AREA_HEIGHT = 50
private const val LIVES = 2
private const val STARS_AMOUNT = 5
private const val STAR_SIZE_SCALE = 1.8F
private const val STAR_BASIC_SPAWN_TIME = 1000L
private const val ANIMATION_NAME = "translationY"
private const val CURRENT_STAGE = 1

private var deviceHeight = 0
private var deviceWidth = 0
private var starsCounter = 0
private var currentLivesAmount = LIVES
private var currentScore = 0
private var currentSign = 0
private var delayDuration = BASIC_DELAY_DURATION
private var fallingDuration = BASIC_FALLING_DURATION
private var animationName = ANIMATION_NAME
private var currentStage = CURRENT_STAGE
private val waitToCheck = Handler()
lateinit var mainHandler:Handler
lateinit var anim:ObjectAnimator

class NewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_1)
        val startingGameShowText = 0
        configuration(currentStage)
        currentSign = getDefaultSymbol(this)
        mainHandler = Handler(Looper.getMainLooper())
        startGameCountDown(startingGameShowText, this, switcher)
        getDeviceResolution()
        waitToCheck.postDelayed({
            starCreatingWithDelay()
        }, startTextCountdown)
    }

    private fun starCreatingWithDelay() {
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, STAR_BASIC_SPAWN_TIME)
                createStarAnimation()
            }
        })
    }

    private fun createStarAnimation() {
        val star = ImageButton(this)
        star.background = ResourcesCompat.getDrawable(resources, currentSign, null)
        star.scaleX = STAR_SIZE_SCALE
        star.scaleY = STAR_SIZE_SCALE
        star.z = -1f
        star.translationX = (deviceWidth * Math.random()).toFloat()

        val myDynamicLayout = findViewById<ConstraintLayout>(R.id.dynamicLayout)
        myDynamicLayout.addView(star)
        anim = ObjectAnimator.ofFloat(star, animationName, deviceHeight.toFloat())
        anim.duration = fallingDuration
        anim.startDelay = delayDuration * Math.random().toLong()

        anim.doOnEnd {
            if (!checkLivesLeft(currentLivesAmount, livesLeft)) {
                stopTheGame(anim, switcher)
                destroyAnimations()
                switcher.setText(resources.getText(R.string.game_over))
                go_next.visibility = View.VISIBLE
                go_next.setOnClickListener { enterNameDialog(this, currentScore, currentStage) }
            } else {
                currentLivesAmount--
                starsCounter++
            }
        }
        star.setOnClickListener {
            anim.removeAllListeners()
            currentScore = addScore(currentScore, score)
            star.visibility = View.GONE
            starsCounter++
        }
        anim.start()
        addAnimation(anim, star)

        if (starsCounter >= STARS_AMOUNT) {
            stopTheGame(anim, switcher)
            destroyAnimations()
            switcher.setText(resources.getText(R.string.level_finished))
            go_next.visibility = View.VISIBLE
            go_next.setOnClickListener {
                finish()
                startActivity(Intent(this, NewGame::class.java))
            }
        }
    }

    private fun getDeviceResolution() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        deviceHeight = displayMetrics.heightPixels - DECREASE_STARS_SPAWN_AREA_HEIGHT
        deviceWidth = displayMetrics.widthPixels - DECREASE_STARS_SPAWN_AREA_WIDTH
    }

    override fun onBackPressed() {
        mainHandler.removeCallbacksAndMessages(null)
        pauseAnimations()
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.leave_msg).setTitle(R.string.leave_title)
            .setPositiveButton(R.string.stay_msg) { dialog, _ ->
                dialog.cancel()
                resumeAnimations()
                starCreatingWithDelay()
            }
            .setNegativeButton(R.string.leave_msg) { _, _ ->
                finish()
            }
        val showDialog = dialogBuilder.create()
        showDialog.show()
    }

    private fun configuration(stage: Int){
        when (stage){
            1 ->{
                currentScore = 0
            }
            2 -> {
                dynamicLayout.background = resources.getDrawable(R.drawable.egip)
                anim.duration = fallingDuration-1000
            }
            3 -> {
                dynamicLayout.background = resources.getDrawable(R.drawable.egipt)
                anim.duration = fallingDuration-500
            }
        }
        starsCounter = 0
        livesLeft.text = currentLivesAmount.toString()
        score.text = currentScore.toString()
        stage_score.text = currentStage.toString()
        go_next.visibility = View.GONE
        currentStage++
    }
}
