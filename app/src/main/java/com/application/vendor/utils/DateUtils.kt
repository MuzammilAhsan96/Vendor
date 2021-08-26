package com.application.vendor.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun setDateFormat(
        sDate: String?,
        oFormat: String?
    ): String {
        var str = ""
        try {
            if (!TextUtils.isEmpty(sDate)) {
                val format = SimpleDateFormat(AppConstant.DETECT_FORMAT_UTC)
                format.timeZone = TimeZone.getTimeZone("UTC")
                val outputFormat = SimpleDateFormat(oFormat)
                var date: Date? = null
                try {
                    date = format.parse(sDate)
                    str =
                        outputFormat.format(Objects.requireNonNull(date))
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
        }
        return str
    }

}