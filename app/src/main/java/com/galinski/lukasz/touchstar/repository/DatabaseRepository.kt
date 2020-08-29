package com.galinski.lukasz.touchstar.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.galinski.lukasz.touchstar.model.ScoreModel
import com.galinski.lukasz.touchstar.repository.database.ScoreDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

    fun insertScore(context: Context, scoreModel: ScoreModel): Disposable{
        return Observable.fromCallable { ScoreDatabase.loadInstance(context).scoreDao().insertScore(scoreModel) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun setGames(context: Context) {
        data = ArrayList()
        data = ScoreDatabase.loadInstance(context).scoreDao().getScoreList() as ArrayList<ScoreModel>
    }
}