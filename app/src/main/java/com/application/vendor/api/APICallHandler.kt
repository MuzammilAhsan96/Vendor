package com.application.vendor.api

import com.application.vendor.model.base.Errors

interface APICallHandler<T> {

    fun onSuccess(apiType: APIType, response: T?)

    fun onFailure(apiType: APIType, error: Errors)
}