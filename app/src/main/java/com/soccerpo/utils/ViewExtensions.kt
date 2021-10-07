package com.soccerpo.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

//fun FragmentManager.switch(containerId: Int, newFrag: Fragment, tag: String) {
//
//    var current = findFragmentByTag(tag)
//    beginTransaction()
//        .apply {
//
//            //Hide the current fragment
//            primaryNavigationFragment?.let { hide(it) }
//
//            //Check if current fragment exists in fragmentManager
//            if (current == null) {
//                current = newFrag
//                add(containerId, current!!, tag)
//            } else {
//                show(current!!)
//            }
//        }
//        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        .setPrimaryNavigationFragment(current)
//        .setReorderingAllowed(true)
//        .commitNowAllowingStateLoss()
//}

fun TabLayout.tabShow(){
    visibility = View.VISIBLE
}

fun TabLayout.tabHide(){
    visibility = View.GONE
}

fun RecyclerView.show(){
    visibility = View.VISIBLE
}

fun RecyclerView.hide(){
    visibility = View.GONE
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok"){
            snackbar.dismiss()
        }
    }.show()
}

fun ImageView.show(){
    visibility = View.VISIBLE
}

fun ImageView.hide(){
    visibility = View.GONE
}

