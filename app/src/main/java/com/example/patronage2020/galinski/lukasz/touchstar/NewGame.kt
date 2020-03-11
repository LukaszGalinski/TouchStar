package com.example.patronage2020.galinski.lukasz.touchstar

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextSwitcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.patronage2020.galinski.lukasz.touchstar.database.DatabaseData
import com.example.patronage2020.galinski.lukasz.touchstar.database.DatabaseHandler
import kotlinx.android.synthetic.main.alertdialog_customview.view.*
import kotlinx.android.synthetic.main.level_1.*
import kotlinx.android.synthetic.main.namedialog_customview.view.*

private const val GAME_START_CHARACTER_SHOWTIME = 1000L
private const val BASIC_FALLING_DURATION = 4999L
private const val BASIC_DELAY_DURATION = 1000L
private const val DELAY_DURATION_DECREASE_PERLVL = 200L
private const val FALLING_DURATION_DECREASE_PERLVL = 400L
private const val DECREASE_STARS_SPAWN_AREA_WIDTH = 100     //Secure stars spawning in the outside of the screen
private const val DECREASE_STARS_SPAWN_AREA_HEIGHT = 50
private const val LIVES = 2
private const val STARS_AMOUNT = 5
private const val STAR_SIZE_SCALE = 1.8F
private const val STAR_BASIC_SPAWN_TIME = 1000L
private const val ANIMATION_NAME = "translationY"
private const val CURRENT_STAGE = 1
private const val MAX_STAGE = 3

private var deviceHeight = 0
private var deviceWidth = 0
private var starsCounter = 0
private var currentLivesAmount = LIVES
private var currentScore = 0
private var currentSign = 0
private var backPressingCounter = 1
private var delayDuration = BASIC_DELAY_DURATION
private var fallingDuration = BASIC_FALLING_DURATION
private var animationName = ANIMATION_NAME
private var currentStage = CURRENT_STAGE
lateinit var mainHandler:Handler
private val switcherMoves = Handler()
private val textToShow = arrayOf("3", "2", "1", "START")

class NewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_1)
        val startingGameShowText = 0
        backPressingCounter = 1
        stageSetUp(currentStage)
        mainHandler = Handler(Looper.getMainLooper())
        startGameCountDown(startingGameShowText, this, switcher)
        getDeviceResolution()
    }

    private fun createStarInLoop() {
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, STAR_BASIC_SPAWN_TIME)

                createObjectWithAnimation()
            }
        })
    }

    private fun startGameCountDown(startingGameShowText: Int, context: Context, switcher: TextSwitcher) {
        var textCountDown = startingGameShowText
        switcher.setInAnimation(context, android.R.anim.slide_in_left)
        switcher.setOutAnimation(context, android.R.anim.slide_out_right)
        switcherMoves.postDelayed({
            if (textCountDown == textToShow.size) {
                switcher.visibility = View.GONE
                createStarInLoop()
            } else {
                switcher.setText(textToShow[textCountDown])
                startGameCountDown(++textCountDown, context, switcher)
            }
        }, GAME_START_CHARACTER_SHOWTIME)
    }

    private fun createObjectWithAnimation() {
        val star = ImageButton(this)
        star.background = ResourcesCompat.getDrawable(resources, currentSign, null)
        star.scaleX = STAR_SIZE_SCALE
        star.scaleY = STAR_SIZE_SCALE
        star.z = -1f
        star.translationX = (deviceWidth * Math.random()).toFloat()

        val myDynamicLayout = findViewById<ConstraintLayout>(R.id.dynamicLayout)
        myDynamicLayout.addView(star)
        val animation = PropertyValuesHolder.ofFloat(animationName, deviceHeight.toFloat())
        val anim = ObjectAnimator.ofPropertyValuesHolder(star, animation)
        anim.duration = fallingDuration
        anim.startDelay = delayDuration * Math.random().toLong()
        anim.doOnEnd {
            if (!checkLivesLeft(currentLivesAmount, livesLeft)) {
                stopTheGame(anim, switcher)
                destroyAnimations()
                switcher.setText(resources.getText(R.string.game_over))
                go_next.visibility = View.VISIBLE
                go_next.setOnClickListener {
                    endGameDialog(this, currentScore, currentStage)
                    currentStage = 1
                }
        } else {
            currentLivesAmount--
            starsCounter++
        }
    }

        anim.start()
        addAnimationToList(anim, star)
        star.setOnClickListener {
            anim.removeAllListeners()
            currentScore = addScore(currentScore, score)
            star.visibility = View.GONE
            starsCounter++
        }

        if (starsCounter > STARS_AMOUNT) {
            stopTheGame(anim, switcher)
            destroyAnimations()
            switcher.setText(resources.getText(R.string.level_finished))
            go_next.visibility = View.VISIBLE
            go_next.setOnClickListener {
                if (currentStage != MAX_STAGE + 1) {
                    finish()
                    startActivity(Intent(this, NewGame::class.java))
                }else{
                    endGameDialog(this, currentScore, currentStage)
                    currentStage = 1
                }
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
        switcherMoves.removeCallbacksAndMessages(null)
        switcher.visibility = View.GONE
        pauseAnimations()
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.alertdialog_customview, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val alertDialog = builder.show()
        dialogView.button_stay.setOnClickListener {
            alertDialog.dismiss()
            resumeAnimations()
            createStarInLoop()
        }
        dialogView.button_back.setOnClickListener {
            currentStage = 1
            alertDialog.dismiss()
            finish()
        }
        alertDialog.setOnCancelListener{
            resumeAnimations()
            createStarInLoop()
        }
    }

    private fun stageSetUp(stage: Int) {
        when (stage) {
            1 -> {
                currentSign = getDefaultSymbol(this)
                currentScore = 0
                delayDuration = BASIC_DELAY_DURATION
                fallingDuration = BASIC_FALLING_DURATION
                currentLivesAmount = LIVES
            }
            2 -> {
                dynamicLayout.background = ContextCompat.getDrawable(this, R.drawable.egip)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.level2_upper_bar))
                bottomBar.setBackgroundColor(ContextCompat.getColor(this, R.color.level2_bottom_bar))

            }
            3 -> {
                dynamicLayout.background = ContextCompat.getDrawable(this, R.drawable.egipt)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.level3_upper_bar))
                bottomBar.setBackgroundColor(ContextCompat.getColor(this, R.color.level3_bottom_bar))
            }
        }
        starsCounter = 0
        delayDuration -= DELAY_DURATION_DECREASE_PERLVL
        fallingDuration -= FALLING_DURATION_DECREASE_PERLVL
        livesLeft.text = currentLivesAmount.toString()
        score.text = currentScore.toString()
        stage_score.text = currentStage.toString()
        go_next.visibility = View.GONE
        currentStage++
    }

    private fun endGameDialog(context: Context, currentScore: Int, currentStage: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.namedialog_customview, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        val alertDialog = builder.show()
        dialogView.button_save.setOnClickListener {
            val userName = dialogView.name_edtTxt.text.toString()
            if (nameValidation(userName)) {
                val data =
                    DatabaseData(
                        userName,
                        currentScore.toLong(),
                        currentStage
                    )
                DatabaseHandler(
                    context
                ).insertScore(data)
                alertDialog.dismiss()
                startActivity(Intent(context, MainMenu::class.java))
            } else {
                Toast.makeText(context, context.resources.getText(R.string.username_rule), Toast.LENGTH_SHORT).show()
            }
        }
        alertDialog.setOnCancelListener {
            startActivity(Intent(context, MainMenu::class.java))
        }
    }
}
