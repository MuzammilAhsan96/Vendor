package com.application.vendor.model.food

import java.io.Serializable


class Food : Serializable {

    var name: String? = null
    var foodId: String? = null
    var price: Float = 0.0f
    var quantity: Int = 0
    var total: Float = 0.0f

    var status: Int = 0
    var isAvailable: Int = 0
    var availableQuantity: Int = 0
    var images: MutableList<String>? = null
    var isDeleted: Int? = null
    var description: String? = null

}