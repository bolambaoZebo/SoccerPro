package com.soccerpo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soccerpo.data.db.entity.Soccer

@Dao
interface SoccerDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savaAllSoccer(horseVideo: List<Soccer>)

    @Query("SELECT * FROM soccer_news")
    fun getSoccer() : LiveData<List<Soccer>>

    @Query("SELECT * FROM soccer_news WHERE teamLeague LIKE :searchQuery")//firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Soccer>>

}