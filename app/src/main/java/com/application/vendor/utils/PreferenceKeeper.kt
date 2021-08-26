package com.application.vendor.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.application.vendor.BuildConfig
import com.application.vendor.base.App
import com.application.vendor.model.api.user.UserProfile

class PreferenceKeeper() {
    init {
        prefs = App.INSTANCE.getSharedPreferences(
            BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE
        )
    }

    companion object {
        private lateinit var prefs: SharedPreferences
        private lateinit var keeper: PreferenceKeeper
        fun getInstance(): PreferenceKeeper {
            keeper = PreferenceKeeper()
            return keeper
        }
    }

    var fcmToken: String?
        get() {
            val fcmToken = prefs.getString("fcmToken", "")
            return fcmToken.toString()
        }
        set(fcmToken) {
            prefs.edit().putString("fcmToken", fcmToken).apply()
        }

    var accessToken: String?
        get() {
            var accessToken = prefs.getString(AppConstant.PKN.ACCESS_TOKEN, "")
            if (TextUtils.isEmpty(accessToken)) {
                accessToken = ""
            }
            return accessToken.toString()
        }
        set(accessToken) {
            prefs.edit().putString(AppConstant.PKN.ACCESS_TOKEN, accessToken).apply()
        }


    var isLogin: Boolean
        get() {
            return prefs.getBoolean("IS_LOGIN", false)
        }
        set(isLogin) {
            prefs.edit().putBoolean("IS_LOGIN", isLogin).apply()
        }


    fun getUser(): UserProfile? {
        return Gson().fromJson(prefs.getString("user", ""), UserProfile::class.java)
    }

    fun setUser(user: UserProfile?) {
        prefs.edit().putString("user", Gson().toJson(user)).apply()
    }
}