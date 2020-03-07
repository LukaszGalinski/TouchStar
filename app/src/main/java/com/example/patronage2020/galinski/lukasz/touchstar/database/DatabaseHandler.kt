package com.example.patronage2020.galinski.lukasz.touchstar.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "TouchStarDatabase"
private const val TABLE_NAME = "ScoreTable"
private const val KEY_ID = "userId"
private const val KEY_NAME = "userName"
private const val KEY_SCORE = "score"
private const val KEY_STAGE = "stage"


class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableScore = ("CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, $KEY_SCORE LONG, $KEY_STAGE INTEGER)")
        db?.execSQL(createTableScore)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertScore(data: DatabaseData): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, data.userName)
        contentValues.put(KEY_SCORE, data.score)
        contentValues.put(KEY_STAGE, data.stage)
        val addDataSuccess = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return addDataSuccess
    }

    fun fetchScore(): List<DatabaseData>{
        val scoreList: ArrayList<DatabaseData> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $KEY_SCORE DESC LIMIT 7"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException){
            db.execSQL(query)
        }
        var userId: Int
        var userName: String
        var score: Long
        var stage: Int

        if (cursor != null && cursor.moveToFirst()){
            do {
                userId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                userName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                score = cursor.getLong(cursor.getColumnIndex(KEY_SCORE))
                stage = cursor.getInt(cursor.getColumnIndex(KEY_STAGE))
                val data  =
                    DatabaseData(
                        userId,
                        userName,
                        score,
                        stage
                    )
                scoreList.add(data)
            }while (cursor.moveToNext())
        }
        cursor?.close()
        return scoreList
    }
}