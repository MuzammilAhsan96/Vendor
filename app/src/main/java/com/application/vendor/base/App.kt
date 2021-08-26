package com.application.vendor.base

import android.app.Application

class App : Application() {

   public companion object {
       public  var INSTANCE: Application = App()
    }

    public  override fun onCreate() {
        super.onCreate()
        INSTANCE = this@App
    }
}