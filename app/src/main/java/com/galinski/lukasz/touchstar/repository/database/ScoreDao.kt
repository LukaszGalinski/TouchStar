package com.galinski.lukasz.touchstar.repository.database

import androidx.room.*
import com.galinski.lukasz.touchstar.model.ScoreModel

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score ORDER BY score DESC LIMIT 7")
    fun getScoreList(): List<ScoreModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(scoreModel: ScoreModel)

    @Query("DELETE FROM score WHERE userId IS NOT (SELECT userId FROM score ORDER BY score DESC LIMIT 7)")
    fun deleteNotUsedRecords()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(scoreList: List<ScoreModel>)
}
