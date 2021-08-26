package com.application.vendor.model.api.user

import java.io.Serializable


class UserProfile : Serializable {
    var accessToken: String? = null
    var employeeId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var empId: String? = null
    var email: String? = null
    var employeePhone: String? = null
    var profileImage: String? = null
    var couponWalletBalance: Float = 0.0f
    var walletBalance: Float = 0.0f
    var phone: String? = null

}