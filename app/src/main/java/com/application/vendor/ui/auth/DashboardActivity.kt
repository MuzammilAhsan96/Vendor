package com.application.vendor.ui.auth

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.application.vendor.R
import com.application.vendor.base.BaseActivity
import com.application.vendor.callback.RootCallback
import com.application.vendor.model.food.Food
import com.application.vendor.ui.splash.SplashActivity
import com.application.vendor.utils.AppConstant
import com.application.vendor.utils.AppUtil
import com.application.vendor.utils.PreferenceKeeper
import com.application.vendor.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.nav_header_main.*

class DashboardActivity : BaseActivity(), RootCallback<Any>, SwipeRefreshLayout.OnRefreshListener {

    private var foodAdapter: DashboardAdapter? = null

    private val viewModel: AuthViewModel by viewModels()

    var vendorId: String? = null

    private var foods: MutableList<Food>? = null
    override fun layoutRes(): Int {
        return R.layout.activity_dashboard
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMyProfile()
        setNav()
        setListener()
        setAdapter()
        setObservables()

        if (AppUtil.isConnection()) {
            showProgressBar()
            viewModel.getFoodList(vendorId)
        }

        btnAdd.setOnClickListener {
            launchActivity(AddActivity::class.java)
        }
    }

    private fun setListener() {
        ivMenuK.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        ivCross?.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
        }


        linearLogout?.setOnClickListener {
            PreferenceKeeper.getInstance().setUser(null)
            PreferenceKeeper.getInstance().accessToken = null
            PreferenceKeeper.getInstance().isLogin = false
            AppConstant.USER = null
            PreferenceKeeper.getInstance().setUser(null)
            launchActivity(LoginActivity::class.java)
            finishAffinity()
        }

    }

    override fun onRefresh() {
        if (AppUtil.isConnection()) {
            viewModel.getMyProfile()
        }
    }

    override fun onResume() {
        super.onResume()
        if (AppUtil.isConnection()) {
            viewModel.getMyProfile()
        }
    }


    private fun setNav() {
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setAdapter() {
        foodAdapter = DashboardAdapter()
        rvFoodList.setHasFixedSize(true) //every item of the RecyclerView has a fix size
        rvFoodList.adapter = foodAdapter
        foodAdapter?.setRootCallback(this as RootCallback<Any>)
    }


    private fun setObservables() {

        viewModel.myProfileSuccess.observe(this) { data ->
            tvNameK?.text = "Hello ${data.profileData?.firstName}"

            tvMenuName?.text =
                AppUtil.getFullName(data.profileData?.firstName, data.profileData?.lastName)
            tvMenuEmail?.text = data.profileData?.email
            val w = data.profileData?.walletBalance
            tvMenuWallet?.text = w?.let { AppUtil.getRupee(w) }
            tvMenuPhone?.text = data.profileData?.phone
        }


        viewModel.foodListSuccess.observe(this) { data ->
            hideProgressBar()
            foods = data.foodItemList
            foodAdapter?.setData(foods)
        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }

    var itemCount = 0
    var totalPrice = 0.0f

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.REQUEST_CODES.REQ_BUY_NOW_CODE) {
            val b = data?.extras
            val index = b?.getInt(AppConstant.BK.PAYMENT)
            if (resultCode == Activity.RESULT_OK) {
                if (index == 1 || index == 2) { // 1 for payment done, 2 for cancel, null back pressed
                    if (AppUtil.isConnection()) {
                        showProgressBar()
                        viewModel.getFoodList(vendorId)
                    }
                }
            }
        }
    }
}