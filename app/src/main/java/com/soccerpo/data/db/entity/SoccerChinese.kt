package com.soccerpo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soccer_chinese_news")
data class SoccerChinese(
    var id: String? = null,
    var imageUrl: String? = null,
    var title: String? = null,
    var description: String? = null,
    var teamLeague: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = CURRENT_USER_ID
}
