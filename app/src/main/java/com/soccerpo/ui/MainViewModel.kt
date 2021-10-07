package com.soccerpo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soccerpo.data.db.entity.Language
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.repository.SoccerRepository
import com.soccerpo.ui.home.HomeListener
import com.soccerpo.utils.Coroutines
import com.soccerpo.utils.lazyDeferred
import java.util.*

class MainViewModel (
    private val repository: SoccerRepository
) : ViewModel(){

    var mainListener: HomeListener? = null

    fun currentLang() = repository.getLanguageNow()

    fun setLanguage(lang: Language){
        repository.saveLanguage(lang)
    }

    private val _textHighlights = MutableLiveData<String>().apply {
        value = "Shared View Model highlights"
    }
    val textHighlights: LiveData<String> = _textHighlights

    val soccerData by lazyDeferred {
      repository.getSoccerData()
    }

    val videoData by lazyDeferred {
        repository.getVideoData()
    }

    val videoIsActive by lazyDeferred {
        repository.videoDataIsLive
    }

//    fun searchDatabase(searchQuery: String): LiveData<List<Soccer>> {
//        return repository.searchDatabase(searchQuery)
//        Coroutines.main {
//            repository.searchDatabase(searchQuery)
//            return@main
//        }
//    }

//    fun filterSoccerData() : List<Soccer> {
//
//    }

}