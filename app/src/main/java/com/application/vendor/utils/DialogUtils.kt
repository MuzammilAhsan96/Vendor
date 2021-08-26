package com.application.vendor.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.application.vendor.R
import com.application.vendor.base.BaseActivity
import com.application.vendor.callback.DialogClickListener
import java.util.*

object DialogUtils {
    fun showAlertAppSetting(
        activity: BaseActivity,
        permissionMsg: String
    ) {
        val dialog = android.app.AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.app_name))
            .setMessage(permissionMsg)
            .setPositiveButton(activity.getString(R.string.yes)) { dialog1: DialogInterface?, which: Int ->
                launchSetting(activity)
                dialog1?.dismiss()
            }
            .setNegativeButton(activity.getString(R.string.no)) { dialog12: DialogInterface?, which: Int ->
                dialog12?.dismiss()
            }.show()
        dialog.setCanceledOnTouchOutside(false)
        val positiveButton = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
        positiveButton.setTextColor(AppUtil.getColor(R.color.color_black))
        negativeButton.setTextColor(AppUtil.getColor(R.color.color_black))
    }
    fun launchSetting(activity: BaseActivity) {
        val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
        val uri = Uri.fromParts("package", activity.packageName, null as String?)
        intent.data = uri
        activity.startActivityForResult(intent, AppConstant.REQUEST_CODES.APP_SETTING)
    }

    fun showAlert(context: Context, message: String) {
        showAlert(
            context,
            context.resources.getString(R.string.app_name),
            message,
            context.resources.getString(R.string.ok),
            null
        )
    }


    fun showAlert(
        context: Context,
        title: String,
        message: String,
        ok: String?,
        no: String?,
        callback: DialogClickListener<Any>?
    ) {
        val dialog =
            AlertDialog.Builder(context!!)
                .setTitle(Html.fromHtml("<font color='#000'>$title</font>"))
                .setMessage(Html.fromHtml("<font color='#808080'>$message</font>"))
                .setPositiveButton(
                    ok
                ) { dialog1: DialogInterface?, which: Int ->
                    callback?.onClick(true)
                }
                .setNegativeButton(
                    no
                ) { _: DialogInterface?, which: Int ->
                    callback?.onClick(false)
                }.show()
        dialog.setCanceledOnTouchOutside(false)
        val positiveButton =
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton =
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        positiveButton.setTextColor(AppUtil.getColor(R.color.color_black))
        negativeButton.setTextColor(AppUtil.getColor(R.color.color_black))
    }


    fun showAlert(
        context: Context?,
        title: String,
        message: String,
        ok: String?,
        callback: DialogClickListener<Any>?
    ) {
        val dialog =
            AlertDialog.Builder(context!!)
                .setTitle(Html.fromHtml("<font color='#000'>$title</font>"))
                .setMessage(Html.fromHtml("<font color='#808080'>$message</font>"))
                .setPositiveButton(
                    ok
                ) { _: DialogInterface?, which: Int ->
                    callback?.onClick(true)
                }.show()
        dialog.setCanceledOnTouchOutside(false)
        val positiveButton =
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(AppUtil.getColor(R.color.color_black))
    }



    fun playStoreDialog(
        context: BaseActivity?,
        callback: DialogClickListener<Any>?
    ) {
        context?.let { i ->
            val dialog = Dialog(i)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val b2: View =
                LayoutInflater.from(i).inflate(
                    R.layout.progress_layout, null, false
                )
            dialog.setContentView(b2.rootView)
            dialog?.setCanceledOnTouchOutside(false)
            Objects.requireNonNull(dialog.window)
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }
    }
}