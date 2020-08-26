package com.galinski.lukasz.touchstar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Score", indices = [Index(value = ["userId"], unique = true)])
data class ScoreModel(
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "score") val score: Long,
    @ColumnInfo(name = "stage") val stage: Int){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "userId") var userId: Int = 0
    constructor(id: Int, userName: String, score: Long, stage: Int) : this(userName, score, stage){
        this.userId = id
    }
}
