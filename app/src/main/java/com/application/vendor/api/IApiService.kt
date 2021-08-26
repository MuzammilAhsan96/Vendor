package com.application.vendor.api
import com.application.vendor.model.api.user.MyProfileResponse
import com.application.vendor.model.base.BaseResponse
import com.application.vendor.model.api.user.UserProfileResponse
import com.application.vendor.model.food.FoodResponse
import com.application.vendor.model.food.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface IApiService {

    @FormUrlEncoded
    @POST("v1/employee/login")
    fun loginAPI(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<BaseResponse<UserProfileResponse?>>

    @GET("v1/food/list")
    fun getAllFoodAPI(
        @Query("vendorId") foodId: String?
    ): Call<BaseResponse<FoodResponse?>>

    @GET("v1/employee/getMyProfile")
    fun getMyProfile(): Call<BaseResponse<MyProfileResponse?>>


    @Multipart
    @POST("v1/upload/images")
    fun addPhotos(
        @Part mediaFiles: List<MultipartBody.Part>
    ): Call<BaseResponse<ImageResponse?>>


}