package com.soccerpo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soccerpo.data.db.entity.Language
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese
import com.soccerpo.data.db.entity.Videos

@Database(
    entities = [Soccer::class, SoccerChinese::class, Language::class, Videos::class],
    version = 1
)
abstract class AppRoom: RoomDatabase() {

    abstract  fun getSoccerDao() : SoccerDao
    abstract  fun getSoccerChineseDao() : SoccerChineseDao
    abstract  fun getLanguageDao() : LanguageDao
    abstract  fun getVideoDao() : VideoDao

    companion object{

        @Volatile
        private var instance: AppRoom? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppRoom::class.java,
                "MyDatabase.db"
            ).build()
    }
}