package com.application.vendor.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.viewModels
import com.application.vendor.R
import com.application.vendor.base.BaseActivity
import com.application.vendor.ui.home.DashboardActivity
import com.application.vendor.utils.AppUtil
import com.application.vendor.utils.PreferenceKeeper
import com.application.vendor.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    private val viewModel: AuthViewModel by viewModels()
    override fun layoutRes(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        setObservables()
    }

    private fun setListener() {
        btnLogin.setOnClickListener {
            loginAPI()
        }
    }

    private fun loginAPI() {
        if (AppUtil.isConnection()) {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (loginEmailAPI(email, password)) {
                showProgressBar()
                hideSoftKeyBoard()
                viewModel.loginAPI(email, password)
            }
        }
    }

    private fun loginEmailAPI(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            AppUtil.showToast("Email cannot be blank")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            AppUtil.showToast("Invalid Email")
            return false
        } else if (TextUtils.isEmpty(password)) {
            AppUtil.showToast("Password cannot be blank")
            return false
        } else if (password.length < 6) {
            AppUtil.showToast("Password must contain atleast 6 characters")
            return false
        }
        return true
    }

    private fun setObservables() {

        viewModel.loginSuccess.observe(this) { data ->

            AppUtil.showToast(data?.message)
            hideProgressBar()
            PreferenceKeeper.getInstance().isLogin = true
            val useData = data?.employeeProfile
            PreferenceKeeper.getInstance().accessToken = useData?.accessToken
            PreferenceKeeper.getInstance().setUser(useData)
            launchActivity(DashboardActivity::class.java)
            finish()
        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }
}