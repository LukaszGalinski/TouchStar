package com.galinski.lukasz.touchstar.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galinski.lukasz.touchstar.model.ScoreModel

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "ScoreDatabase"

@Database(entities = [(ScoreModel::class)], version = DATABASE_VERSION)
abstract class ScoreDatabase: RoomDatabase(){

    abstract fun scoreDao(): ScoreDao

    companion object{
        private var instance: ScoreDatabase? = null

        fun loadInstance(context: Context): ScoreDatabase {

            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context, ScoreDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance as ScoreDatabase
        }
    }
}