package com.soccerpo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soccerpo.data.repository.SoccerRepository

class MainActivityFactory(
    private val repository: SoccerRepository
) : ViewModelProvider.NewInstanceFactory()  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}