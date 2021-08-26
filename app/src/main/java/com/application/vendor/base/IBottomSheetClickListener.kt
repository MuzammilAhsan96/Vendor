package com.application.vendor.base

import com.application.vendor.model.Media


interface IBottomSheetClickListener {
    fun onBottomSheetItemClicked(
        position: Int,
        type: BottomSheetType?,
        data: Media?
    )
}