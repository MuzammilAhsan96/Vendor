package com.application.vendor.ui.splash

import android.content.Intent
import android.os.Bundle
import com.application.vendor.BuildConfig
import com.application.vendor.R
import com.application.vendor.base.BaseActivity
import com.application.vendor.ui.auth.LoginActivity
import com.application.vendor.utils.AppConstant
import com.application.vendor.utils.PreferenceKeeper
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {


    override fun layoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateActivity()
    }

    private fun navigateActivity() {
        GlobalScope.launch { // context of the parent, main runBlocking coroutine
            delay(AppConstant.SPLASH_DELAY)
            gotoScreen()
        }
    }

    private fun gotoScreen() {

        //val isLogin = PreferenceKeeper.getInstance().isLogin
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}