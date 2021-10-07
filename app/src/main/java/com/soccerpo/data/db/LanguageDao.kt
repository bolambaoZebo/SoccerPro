package com.soccerpo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soccerpo.data.db.entity.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLanguage(language: Language)
//    fun readData(): Flow<List<Person>>

    @Query("SELECT * FROM languages")
    fun getLanguage() : LiveData<Language>
}

