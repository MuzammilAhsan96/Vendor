package com.application.vendor.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.vendor.api.APICallHandler
import com.application.vendor.api.APICallManager
import com.application.vendor.api.APIType
import com.application.vendor.model.Media
import com.application.vendor.model.api.user.MyProfileResponse
import com.application.vendor.model.base.Errors
import com.application.vendor.model.api.user.UserProfileResponse
import com.application.vendor.model.food.FoodResponse
import com.application.vendor.model.food.ImageResponse
import com.application.vendor.utils.AppConstant
import com.application.vendor.utils.AppUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList

class AuthViewModel : ViewModel(), APICallHandler<Any> {


    var loginSuccess =
        MutableLiveData<UserProfileResponse>()

    var foodListSuccess =
        MutableLiveData<FoodResponse>()

    var error =
        MutableLiveData<Errors>()

    var myProfileSuccess =
        MutableLiveData<MyProfileResponse>()

    var imageUploadSuccess =
        MutableLiveData<ImageResponse>()


    fun loginAPI(
        email: String?,
        password: String?
    ) {
        val apiCallManager = APICallManager(APIType.SIGN_IN, this)
        apiCallManager.loginAPI(email, password)
    }

    fun getFoodList(vendorId: String?) {
        val apiCallManager = APICallManager(APIType.FOOD_LIST, this)
        apiCallManager.getAllFoodAPI(vendorId)
    }


    override fun onSuccess(apiType: APIType, response: Any?) {

        when (apiType) {

            APIType.SIGN_IN -> {
                val userProfileResponse = response as UserProfileResponse
                loginSuccess.setValue(userProfileResponse)
            }
            APIType.FOOD_LIST -> {
                val userFoodResponse = response as FoodResponse
                foodListSuccess.setValue(userFoodResponse)
            }
            APIType.ADD_PHOTO -> {
                val imageResponse = response as ImageResponse
                imageUploadSuccess.setValue(imageResponse)
            }
            APIType.USER_PROFILE -> {
                val myProfileResponse = response as MyProfileResponse
                myProfileSuccess.setValue(myProfileResponse)
            }

            else -> {
            }
        }

    }

    fun getMyProfile() {
        val apiCallManager = APICallManager(APIType.USER_PROFILE, this)
        apiCallManager.getMyProfile()
    }

    fun addFoodImageUploadAPI(selectedMediaFiles: List<Media?>?) {

        val multipartList: MutableList<MultipartBody.Part> = ArrayList()

        for (i in selectedMediaFiles!!.indices) {

            val file = File(selectedMediaFiles[i]?.image)

            val requestFileSocialImage = RequestBody.create(
                AppUtil.getMimeType(selectedMediaFiles[i]?.image).toMediaTypeOrNull(), file
            )

            val socialImageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                AppConstant.MT.UPLOAD_MEDIA,
                file.name,
                requestFileSocialImage
            )

            multipartList.add(socialImageMultipart)
        }


        val apiCallManager = APICallManager(APIType.ADD_PHOTO, this)
        apiCallManager.addFoodImageUploadAPI(multipartList)

    }

    override fun onFailure(apiType: APIType, error: Errors) {
        this.error.value = error
    }
}