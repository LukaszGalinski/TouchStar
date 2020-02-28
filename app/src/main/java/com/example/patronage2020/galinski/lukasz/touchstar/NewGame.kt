package com.example.patronage2020.galinski.lukasz.touchstar


import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.newgame.*

private const val STAR_BASIC_SPAWN_TIME = 1000f
private const val FALLING_DURATION = 4999L
private const val DELAY_DURATION = 500L
private const val GAME_START_CHARACTER_SHOWTIME = 1000L
private const val DECREASE_STARS_SPAWN_AREA_WIDTH = 100     //Secure stars spawning in the outside of the screen
private const val DECREASE_STARS_SPAWN_AREA_HEIGHT = 50
private const val ANIMATED_IMAGE = R.drawable.ic_star_black_24dp
private const val LIVES = 3
private const val SCORE_PER_STAR = 15
private const val STARS_AMOUNT = 20
private var deviceHeight = 0;
private var deviceWidth = 0
private var starsCounter = 0
private var startingGameShowText = 0;
private var currentStarSpawnTime:Long = 0; private var currentFallingDuration:Long = 0
private var currentLivesAmount = 0; private var currentScore = 0
val handlerString = Handler()
private lateinit var mainHandler:Handler

class NewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newgame)
        livesLeft.text = LIVES.toString()
        currentStarSpawnTime = STAR_BASIC_SPAWN_TIME.toLong()
        currentFallingDuration = FALLING_DURATION
        currentLivesAmount = LIVES
        score.text = currentScore.toString()
        mainHandler = Handler(Looper.getMainLooper())
        startGameCountingText()
        getDeviceResolution()
    }

    private fun startGameCountingText() {
        val textToShow = arrayOf("3", "2", "1", "START")
        switcher.setInAnimation(this, android.R.anim.slide_in_left)
        switcher.setOutAnimation(this, android.R.anim.slide_out_right)
        handlerString.postDelayed({
            if (startingGameShowText == textToShow.size) {
                switcher.visibility = View.GONE
                starCreatingWithDelay()
            } else {
                switcher.setText(textToShow[startingGameShowText])
                startGameCountingText()
            }
            startingGameShowText++
        }, GAME_START_CHARACTER_SHOWTIME)
    }

    private fun starCreatingWithDelay() {
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, currentStarSpawnTime)
                if (starsCounter++ < STARS_AMOUNT) {
                    createStarAnimation(starsCounter)
                }
            }
        })
    }

    private fun createStarAnimation(starsCounter: Int) {
        val star = ImageButton(this)
        star.background = ResourcesCompat.getDrawable(resources, ANIMATED_IMAGE, null)
        star.scaleX = 1.8F
        star.scaleY = 1.8F
        star.z = -1f
        star.translationX = (deviceWidth * Math.random()).toFloat()

        val myDynamicLayout = findViewById<ConstraintLayout>(R.id.dynamicLayout)
        myDynamicLayout.addView(star)

        val anim = ObjectAnimator.ofFloat(star, "translationY", 0F, deviceHeight.toFloat())
        anim.duration = currentFallingDuration
        anim.startDelay = DELAY_DURATION * Math.random().toLong()
        anim.doOnEnd {
            if (!checkLivesLeft()) {
                stopTheGame(anim)
                switcher.setText(resources.getText(R.string.game_over))
            }
        }
        star.setOnClickListener {
            anim.removeAllListeners()
            addScore()
            star.visibility = View.GONE
        }
        anim.start()
        if (starsCounter == STARS_AMOUNT) {
            stopTheGame(anim)
            switcher.visibility = View.VISIBLE
            switcher.setText(resources.getText(R.string.level_finished))
        }
    }

    private fun addScore(){
        currentScore += SCORE_PER_STAR
        score.text = currentScore.toString()
    }

    private fun getDeviceResolution() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        deviceHeight = displayMetrics.heightPixels - DECREASE_STARS_SPAWN_AREA_HEIGHT
        deviceWidth = displayMetrics.widthPixels - DECREASE_STARS_SPAWN_AREA_WIDTH
    }

    private fun stopTheGame(animation: Animator) {
        animation.removeAllListeners()
        animation.cancel()
        mainHandler.removeCallbacksAndMessages(null)
        switcher.visibility = View.VISIBLE
    }

    private fun checkLivesLeft(): Boolean {
        return if (currentLivesAmount > 0) {
            currentLivesAmount--
            livesLeft.text = currentLivesAmount.toString()
            true
        } else false
    }
}
