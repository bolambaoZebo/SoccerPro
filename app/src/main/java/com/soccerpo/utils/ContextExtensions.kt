package com.soccerpo.utils

import android.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.soccerpo.data.db.entity.Videos
import com.soccerpo.databinding.ActivityMainBinding
import com.soccerpo.databinding.PopupAdsDialogBinding

fun Context.currentLanguage(context: Context) : String{

    val lang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale.country
    }

    return lang.toString()
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.popUpAds(context: Context, videos: String){
    val dialog = Dialog(
        context,
        R.style.Theme_Material_Light_NoActionBar_Fullscreen
    )
    val dialogBind : PopupAdsDialogBinding = PopupAdsDialogBinding.inflate(LayoutInflater.from(context))
    dialog.setContentView(dialogBind.root)
    dialog.setCancelable(false)

    dialogBind.webview.apply {
        this.loadUrl(videos)
        settings.javaScriptEnabled = true
    }

    dialogBind.btnClickhere.setOnClickListener{
        toThreeLink(context)
    }

    dialogBind.imgExit.setOnClickListener {
        dialogBind.webview.removeAllViews()
        dialogBind.webview.destroy()
        dialogBind.webview.clearCache(true)
        dialogBind.webview.clearHistory()
        dialog.dismiss()
    }
    dialog.show()
}

private fun Context.toThreeLink(context: Context){
    val url = "https://asia3we.com/"
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(url)
    ContextCompat.startActivity(context, openURL, null)
}





