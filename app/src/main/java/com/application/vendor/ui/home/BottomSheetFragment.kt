package com.application.vendor.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.vendor.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_bottom.*


class BottomSheetFragment(var callback: cameraGallery) : BottomSheetDialogFragment() {
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

