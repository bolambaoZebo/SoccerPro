package com.soccerpo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "languages")
data class Language(
    var language: String = "en"
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}


