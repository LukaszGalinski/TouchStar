package com.example.patronage2020.galinski.lukasz.touchstar


import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.level_1.*

private const val FALLING_DURATION = 4999L
private const val DELAY_DURATION = 500L
private const val DECREASE_STARS_SPAWN_AREA_WIDTH = 100     //Secure stars spawning in the outside of the screen
private const val DECREASE_STARS_SPAWN_AREA_HEIGHT = 50
private const val LIVES = 1
private const val STARS_AMOUNT = 21
private const val STAR_SIZE_SCALE = 1.8F
private const val STAR_BASIC_SPAWN_TIME = 1000L
private const val ANIMATION_NAME = "translationY"
private const val CURRENT_STAGE = 1
private var deviceHeight = 0
private var deviceWidth = 0
private var starsCounter = 0
private var currentLivesAmount = 0;
private var currentScore = 0
private var currentSign = 0
private val waitToCheck = Handler()
lateinit var mainHandler:Handler

class NewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_1)
        val startingGameShowText = 0
        go_next.setOnClickListener{

        }
        setUpInterface()
        currentSign = getDefaultSymbol(this)
        mainHandler = Handler(Looper.getMainLooper())
        startGameCountDown(startingGameShowText, this, switcher)
        getDeviceResolution()
        waitToCheck.postDelayed({
            starCreatingWithDelay()
        }, startTextCountdown)
    }

    private fun setUpInterface() {
        currentScore = 0
        currentLivesAmount = LIVES
        starsCounter = 0
        livesLeft.text = LIVES.toString()
        score.text = currentScore.toString()
        go_next.visibility = View.GONE
    }

    private fun starCreatingWithDelay() {
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, STAR_BASIC_SPAWN_TIME)
                starsCounter++
                createStarAnimation(starsCounter)
            }
        })
    }

    private fun createStarAnimation(starsCounter: Int) {
        val star = ImageButton(this)
        star.background = ResourcesCompat.getDrawable(resources, currentSign, null)
        star.scaleX = STAR_SIZE_SCALE
        star.scaleY = STAR_SIZE_SCALE
        star.z = -1f
        star.translationX = (deviceWidth * Math.random()).toFloat()

        val myDynamicLayout = findViewById<ConstraintLayout>(R.id.dynamicLayout)
        myDynamicLayout.addView(star)

        val anim = ObjectAnimator.ofFloat(star, ANIMATION_NAME, deviceHeight.toFloat())
        anim.duration = FALLING_DURATION
        anim.startDelay = DELAY_DURATION * Math.random().toLong()
        anim.doOnEnd {
            if (!checkLivesLeft(currentLivesAmount, livesLeft)) {
                stopTheGame(anim, switcher)
                destroyAnimations()
                switcher.setText(resources.getText(R.string.game_over))
                go_next.visibility = View.VISIBLE
                go_next.setOnClickListener { enterNameDialog() }
            } else {
                currentLivesAmount--
            }
        }
        star.setOnClickListener {
            anim.removeAllListeners()
            currentScore = addScore(currentScore, score)
            star.visibility = View.GONE
        }
        anim.start()
        addAnimation(anim, star)

        if (starsCounter == STARS_AMOUNT) {
            stopTheGame(anim, switcher)
            cancelAnimations()
            switcher.setText(resources.getText(R.string.level_finished))
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

    private fun enterNameDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(R.string.enter_your_name)

        val input = EditText(this)
        dialogBuilder.setView(input)

        dialogBuilder.setPositiveButton(R.string.save_score) { dialog, _ ->
            val userName = input.text.toString()
            if (nameValidation(userName)) {
                val data = DatabaseData(userName, currentScore.toLong(), CURRENT_STAGE)
                DatabaseHandler(this).insertScore(data)
                dialog.cancel()
            } else {
                Toast.makeText(this, resources.getText(R.string.username_rule), Toast.LENGTH_SHORT).show()
            }
        }
        val showDialog = dialogBuilder.create()
        showDialog.show()
    }
    private fun nameValidation(name: String): Boolean {
        return name.length in 1..8
    }
}
