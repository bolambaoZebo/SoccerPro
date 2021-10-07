package com.soccerpo.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soccerpo.data.Preferences.PreferenceProvider
import com.soccerpo.data.db.AppRoom
import com.soccerpo.data.db.entity.Language
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese
import com.soccerpo.data.db.entity.Videos
import com.soccerpo.data.network.MyApi
import com.soccerpo.data.network.SafeApiRequest
import com.soccerpo.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

private val MINIMUM_INTERVAL = 6
class SoccerRepository (
    private val api: MyApi,
    private val db: AppRoom,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val soccerData = MutableLiveData<List<Soccer>>()

    private val soccerVideo = MutableLiveData<List<Videos>>()

    var videoDataIsLive = MutableLiveData<Boolean>()

    init {
        saveLanguage(Language("en"))
        soccerData.observeForever{
            saveSoccerData(it)
        }

        soccerVideo.observeForever{
            saveVideoData(it)
        }

    }

    fun searchDatabase(searchQuery: String): LiveData<List<Soccer>> {
        return db.getSoccerDao().searchDatabase(searchQuery)
    }

    fun getLanguageNow() = db.getLanguageDao().getLanguage()

    fun saveLanguage(lang: Language){
        Coroutines.io {
            db.getLanguageDao().saveLanguage(lang)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL//between(savedAt) > MINIMUM_INTERVAL
        } else {
            true
        }
    }

    private fun saveSoccerData(soccerData: List<Soccer>){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Coroutines.io {
                prefs.savelastSavedAt(LocalDateTime.now().toString())
                db.getSoccerDao().savaAllSoccer(soccerData)
            }
        } else {
            Coroutines.io {
                db.getSoccerDao().savaAllSoccer(soccerData)
            }
        }

    }

    suspend fun getSoccerData(): LiveData<List<Soccer>> {
        return withContext(Dispatchers.IO){
            fetchAllHorseData()
            db.getSoccerDao().getSoccer()
        }
    }

    private suspend fun fetchAllHorseData() {
        val lastSavedAt = prefs.getLastSavedAt()
        val response = apiRequest { api.getSoccerList() }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
                soccerData.postValue(response.en!!)
            }
        } else {
            soccerData.postValue(response.en!!)
        }

    }


    private fun saveVideoData(soccerData: List<Videos>){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Coroutines.io {
                prefs.saveVideo(LocalDateTime.now().toString())
                db.getVideoDao().savaAllVideo(soccerData)
            }
        } else {
            Coroutines.io {
                db.getVideoDao().savaAllVideo(soccerData)
            }
        }

    }

    suspend fun getVideoData(): LiveData<List<Videos>> {
        return withContext(Dispatchers.IO){
            fetchAllVideos()
            db.getVideoDao().getVideo()
        }
    }

    private  suspend fun fetchAllVideos(){
        val pageIndex = 1
        val pageLimit = 12
        val lastSavedVideoAt = prefs.getVideo()
        val response = apiRequest { api.getVideoList() }
        videoDataIsLive.postValue(response.isActive!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (lastSavedVideoAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedVideoAt))) {
                soccerVideo.postValue(response.data!!)
            }
        }
//        else {
//            soccerVideo.postValue(response.data!!)
//        }
    }


}

