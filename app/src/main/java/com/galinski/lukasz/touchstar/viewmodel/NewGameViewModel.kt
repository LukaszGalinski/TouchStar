package com.galinski.lukasz.touchstar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.galinski.lukasz.touchstar.model.ScoreModel
import com.galinski.lukasz.touchstar.repository.DatabaseRepository
import com.galinski.lukasz.touchstar.repository.SharedPreferencesRepository

class NewGameViewModel: ViewModel() {
    private var databaseRepo: DatabaseRepository? = null
    private var sharedRepo: SharedPreferencesRepository? = null

    fun instance(){
        if (databaseRepo != null && sharedRepo != null){
            return
        }
        databaseRepo = DatabaseRepository().instance()
        sharedRepo = SharedPreferencesRepository().instance()
    }

    fun insertScore(context: Context, scoreModel: ScoreModel){
        databaseRepo?.insertScore(context, scoreModel)
    }

    fun getDefaultStarSymbol(context: Context): Int?{
        return sharedRepo?.getDefaultStarSymbol(context)
    }
}