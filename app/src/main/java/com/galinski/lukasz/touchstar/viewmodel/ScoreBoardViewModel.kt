package com.galinski.lukasz.touchstar.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.galinski.lukasz.touchstar.model.ScoreModel
import com.galinski.lukasz.touchstar.repository.DatabaseRepository

class ScoreBoardViewModel: ViewModel() {
    private var repo: DatabaseRepository? = null
    private var scoreData: MutableLiveData<List<ScoreModel>>? = null

    fun init(context: Context){
        if (scoreData != null){
            return
        }
        repo = DatabaseRepository()
            .instance()
        scoreData = repo?.instance()?.getGames(context)
    }

    fun getScoreList(): LiveData<List<ScoreModel>>? {
        return scoreData
    }
}