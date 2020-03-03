package com.example.patronage2020.galinski.lukasz.touchstar

class DatabaseData(val userName: String, val score: Long, val stage: Int){
    var userId: Int = 0
    constructor(id: Int, userName: String, score: Long, stage: Int) : this(userName, score, stage){
        this.userId = id
    }
}
