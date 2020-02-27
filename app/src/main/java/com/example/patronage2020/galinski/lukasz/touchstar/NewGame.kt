package com.example.patronage2020.galinski.lukasz.touchstar


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

private const val STAR_BASIC_SPAWN_TIME = 2000f
private const val FALLING_DURATION = 5000L
private const val DELAY_DURATION = 5000L
private const val DECREASE_STARS_SPAWN_AREA_WIDTH = 100     //Secure stars spawning in the outside of the screen
private const val DECREASE_STARS_SPAWN_AREA_HEIGHT = 50
private var deviceHeight = 0
private var deviceWidth = 0
private var starsCounter = -1
private var currentStarSpawnTime:Long = 0
private lateinit var mainHandler:Handler

class NewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newgame)
        currentStarSpawnTime = STAR_BASIC_SPAWN_TIME.toLong()
        mainHandler = Handler(Looper.getMainLooper())
        getDeviceResulution()
        createHandler()
    }

    private fun createHandler(){
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, currentStarSpawnTime)
                starsCounter++
                createStar(starsCounter)
            }
        })
    }

    private fun createStar(starsCounter: Int) {
        val star = ImageButton(this)
        star.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_star_black_24dp, null)
        star.scaleX = 1.3F
        star.scaleY = 1.3F
        star.id = starsCounter

        val myDynamicLayout = findViewById<RelativeLayout>(R.id.dynamicLayout)
        myDynamicLayout.addView(star)
        star.translationX = (deviceWidth * Math.random()).toFloat()
        star.animate().setDuration(FALLING_DURATION).translationY(deviceHeight.toFloat())
            .setStartDelay((DELAY_DURATION * Math.random().toLong()))

        star.setOnClickListener { v ->
            println("Star pressed: " + star.id)
            star.visibility = View.GONE
        }

        if (starsCounter%10 == 9){
            println("Wynik: " + starsCounter%10)
            mainHandler.removeCallbacksAndMessages(null)
            currentStarSpawnTime -=500
            createHandler()

        }
    }

    private fun getDeviceResulution(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        deviceHeight = displayMetrics.heightPixels-DECREASE_STARS_SPAWN_AREA_HEIGHT
        deviceWidth = displayMetrics.widthPixels-DECREASE_STARS_SPAWN_AREA_WIDTH
    }
}
