package com.soccerpo.data.Preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"
private const val KEY_SAVED_IS_ACTIVE = "key_saved_is_active"

private const val KEY_SAVED_VIDEO_AT = "key_saved_video_at"

class PreferenceProvider(
    context: Context
)  {
    private  val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun savelastSavedAt(savedAt: String){
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getLastSavedAt() : String? {
        return preference.getString(
            KEY_SAVED_AT,
            null)
    }

    fun saveIsActive(isActive: Boolean){
        preference.edit().putBoolean(
            KEY_SAVED_IS_ACTIVE,
            isActive
        ).apply()
    }

    fun getIsActive() : Boolean? {
        return preference.getBoolean(
            KEY_SAVED_IS_ACTIVE,
            false)
    }

    fun saveVideo(saveVideoAt: String?){
        preference.edit().putString(
            KEY_SAVED_VIDEO_AT,
            saveVideoAt
        ).apply()
    }

    fun getVideo() : String? {
        return preference.getString(
            KEY_SAVED_VIDEO_AT,
            null)
    }
}