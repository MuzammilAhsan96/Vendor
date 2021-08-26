package com.application.vendor.ui.auth


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.vendor.R
import com.application.vendor.utils.AppConstant
import com.application.vendor.utils.AppConstant.REQUEST_CODE
import com.application.vendor.utils.AppUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_bottom.*


class BottomSheetFragment(var callback:cameraGallery) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtCamera.setOnClickListener {
            callback.onclick(1)

        }
        txtGallery.setOnClickListener {
            callback.onclick(2)
        }
    }


    interface cameraGallery{
        fun onclick(v:Int)
    }
}

