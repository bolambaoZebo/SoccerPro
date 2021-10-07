package com.soccerpo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "soccer_videos")
data class Videos(
    var competition: String? = null,
    var title: String? = null,
    var thumbnail: String? = null,
    var video: String? = null,
    var date: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = CURRENT_USER_ID
}