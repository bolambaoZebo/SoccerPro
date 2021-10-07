package com.soccerpo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese

@Dao
interface SoccerChineseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savaAllSoccerChinese(soccerChineseList: List<Soccer>)

    @Query("SELECT * FROM soccer_chinese_news")
    fun getSoccerChinese() : LiveData<List<Soccer>>
}