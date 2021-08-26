package com.application.vendor.extension

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

fun View.gone() {
    this.visibility = View.GONE
}


fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun View.visible() {
    this.visibility = View.VISIBLE
}


fun Activity.makeFullScreen(isNoLimit: Boolean) {

    if (isNoLimit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        val decor = window.decorView
        decor.systemUiVisibility = 1
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            /* window?.insetsController?.let {
                 it.hide(WindowInsets.Type.statusBars())
                 it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
             }*/
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}
