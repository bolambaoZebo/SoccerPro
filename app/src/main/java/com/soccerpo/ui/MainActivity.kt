package com.soccerpo.ui

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat.animate
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.soccerpo.R
import com.soccerpo.databinding.ActivityMainBinding
import com.soccerpo.utils.Coroutines
import com.soccerpo.utils.tabHide
import com.soccerpo.utils.tabShow
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import android.animation.AnimatorListenerAdapter
import androidx.annotation.RequiresApi
import com.soccerpo.data.db.entity.Language
import com.soccerpo.ui.home.HomeListener
import com.soccerpo.utils.toast


private const val ENGLISH_LANGUAGE = 0
private const val CHINESE_LANGUAGE = 1

private const val VIEW_VISIBLE = 0
private const val VIEW_GONE = 8

private const val LOCAL_ENGLISH = "en"
private const val LOCAL_CHINESE = "zh"

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: MainActivityFactory by instance()
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var navController: NavController
    private lateinit var local: Locale


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // BIND ACTIVITY
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // SETUP AUTOCOMPLETEVIEW ADAPTER
        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item_languages, languages)
        binding.autoComplete.setAdapter(arrayAdapter)

        //SETUP NAVIGATION VIEW
        val navView: BottomNavigationView = binding.navView
        tabLayout = binding.tabBar
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    navigateToFragment(R.id.navigation_home, View.VISIBLE)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    navigateToFragment(R.id.navigation_dashboard, View.GONE)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    navigateToFragment(R.id.navigation_notifications, View.GONE)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


        //ALL ONCLICK FUNCTIONALITY

        binding.autoComplete.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                ENGLISH_LANGUAGE -> {
                    setLocale(LOCAL_ENGLISH)
                     }
                CHINESE_LANGUAGE -> {
                    setLocale(LOCAL_CHINESE)
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mainViewModel.mainListener?.filterData(translate(tab?.text.toString()), LOCAL_ENGLISH)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }
    // NETWORK CALL HERE

    private fun translate(message: String): String {
        return when(message){
            "全部" -> "All"
            "英超" -> "Premier League"
            "欧冠" -> "UEFA Champions League"
            "欧联" -> "Europa League"
            "西甲" -> "La Liga"
            "意甲" -> "Series A"
            "德甲" -> "Bundesliga"
            "法甲" -> "French League"
            "其他" -> "Others"
            else -> message
        }
    }

    private fun navigateToFragment(destination: Int, toogleView: Int) {
        navController.navigate(destination)
        when (toogleView) {
            VIEW_VISIBLE -> tabLayout.tabShow()
            VIEW_GONE -> tabLayout.tabHide()
        }
    }

    private fun setLocale(language: String) {
        local = Locale(language)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration

        val currentLang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conf.locales[0]
        } else {
            applicationContext.resources.configuration.locale.country
        }

        val newCurrent = currentLang.toString().split("_")

        if (currentLang.toString() != LOCAL_ENGLISH && currentLang.toString() != LOCAL_CHINESE) {
            if (newCurrent[0] != language) {
                setLocalLanguage(dm, conf, res, language)
            }
        } else {
            if (currentLang.toString() != language) {
                setLocalLanguage(dm, conf, res, language)
            }
        }
    }

    private fun setLocalLanguage(dm: DisplayMetrics, conf: Configuration, res: Resources, language: String) {
        conf.setLocale(local)
        res.updateConfiguration(conf, dm)
        refreshIntent(this, MainActivity::class.java)
        mainViewModel.setLanguage(Language(language))
    }

    private fun refreshIntent(context: Context, java: Class<MainActivity>) {
        val refresh = Intent(context, java)
        startActivity(refresh)
        finish()
    }

}

