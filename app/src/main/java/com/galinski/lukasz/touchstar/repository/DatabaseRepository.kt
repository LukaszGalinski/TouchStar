package com.galinski.lukasz.touchstar.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.galinski.lukasz.touchstar.model.ScoreModel
import com.galinski.lukasz.touchstar.repository.database.ScoreDatabase

class DatabaseRepository {
    private var instance: DatabaseRepository? = null
    private var dataSet = MutableLiveData<List<ScoreModel>>()
    private lateinit var data: ArrayList<ScoreModel>

    fun instance(): DatabaseRepository? {
        instance ?: synchronized(this){
            instance ?: DatabaseRepository()
                .also { instance = it }
        }
        return instance
    }

    fun getGames(context: Context): MutableLiveData<List<ScoreModel>>{
        setGames(context)
        dataSet.postValue(data)
        return dataSet
    }

    fun insertScore(context: Context, scoreModel: ScoreModel){
        ScoreDatabase.loadInstance(context).scoreDao().insertScore(scoreModel)
    }

    private fun setGames(context: Context) {
        data = ArrayList()
        data = ScoreDatabase.loadInstance(
            context
        ).scoreDao().getScoreList() as ArrayList<ScoreModel>
    }
}