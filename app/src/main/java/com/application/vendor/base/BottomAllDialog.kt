package com.application.vendor.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.application.vendor.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.application.vendor.model.Media
import kotlinx.android.synthetic.main.bottom_dialog_all.view.*

class BottomAllDialog :
    BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<*>? = null

    private var dialog: BottomSheetDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.CustomBottomSheetDialogTheme
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val binding = LayoutInflater.from(activity)
            .inflate(R.layout.bottom_dialog_all, null, false)

        dialog!!.setContentView(binding)

        mBehavior =
            BottomSheetBehavior.from(
                binding.parent as View
            )

        binding.tvAppName.text = activity.getString(R.string.app_name)

        if (type != null) {
            when (type) {
                BottomSheetType.CAMERA_GALLERY -> binding.tvTitle.text =
                    getString(R.string.select_media_type)
                else -> binding.tvTitle.text = "Select Type"
            }
        }
        val adapter =
            BottomAllAdapter(type, object : IBottomSheetClickListener {
                override fun onBottomSheetItemClicked(
                    position: Int,
                    type: BottomSheetType?,
                    data: Media?
                ) {
                    dismiss()

                    listener.onBottomSheetItemClicked(position, type, data)
                }
            })
        adapter.updateData(storageBottomList)
        binding.recyclerView.adapter = adapter
        binding.tvCancel.setOnClickListener { v: View? -> dismiss() }


        return dialog!!
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var storageBottomList: List<Media>? = null
    private lateinit var activity: BaseActivity

    private lateinit var listener: IBottomSheetClickListener

    private var type: BottomSheetType? = null

    fun setContext(
        activity: BaseActivity,
        storageBottomList: List<Media>?,
        type: BottomSheetType?,
        listener: IBottomSheetClickListener
    ) {
        this.activity = activity
        this.storageBottomList = storageBottomList
        this.listener = listener
        this.type = type
    }
}