package com.soccerpo

import android.app.Application
import com.soccerpo.data.Preferences.PreferenceProvider
import com.soccerpo.data.db.AppRoom
import com.soccerpo.data.network.MyApi
import com.soccerpo.data.network.NetworkConnectionInterceptor
import com.soccerpo.data.repository.SoccerRepository
import com.soccerpo.ui.MainActivityFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainKodinActivity : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainKodinActivity))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi( instance()) }
        bind() from singleton { AppRoom( instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { SoccerRepository( instance(), instance(), instance()) }
        bind() from provider { MainActivityFactory( instance()) }
//        bind() from singleton { UserRepository( instance(), instance()) }
//        bind() from singleton { HorseRaceRepository( instance(), instance(), instance()) }
//        bind() from provider { GalleryViewFactory( instance()) }
//        bind() from provider { HomeViewFactory( instance()) }
//        bind() from provider { AuthViewModelFactory( instance()) }
    }
}