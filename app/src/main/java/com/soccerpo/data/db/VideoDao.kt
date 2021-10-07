package com.soccerpo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soccerpo.data.db.entity.Videos

@Dao
interface VideoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savaAllVideo(videData: List<Videos>)

    @Query("SELECT * FROM soccer_videos")
    fun getVideo() : LiveData<List<Videos>>

}