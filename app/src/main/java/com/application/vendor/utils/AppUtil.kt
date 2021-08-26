package com.application.vendor.utils


import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.application.vendor.R
import com.application.vendor.base.App
import com.application.vendor.base.App.Companion.INSTANCE
import com.application.vendor.model.Media
import com.application.vendor.model.api.user.UserProfile
import java.io.File
import java.util.*
import java.util.regex.Pattern

object AppUtil {


    /** Method to Get user data from constant if not present then find from preference @return user object **/
    fun getUser(): UserProfile? {
        var user = AppConstant.USER
        if (user?.firstName == null) {
            user = PreferenceKeeper.getInstance().getUser()
            AppConstant.USER = user
        }
        return user
    }


    fun showToast(message: String?) {
        Toast.makeText(INSTANCE, message, Toast.LENGTH_SHORT).show()
    }

    /** Method for displaying snackbar message**/
    fun showSnackBar(view: View, message: String) {
        val snackbar: Snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    /** Method for preventing multiple Clicks **/
    fun preventTwoClick(view: View) {
        view.setEnabled(false)
        view.postDelayed(Runnable { view.setEnabled(true) }, 800)
    }


    fun isConnection(): Boolean {
        return isConnection(true)
    }

    fun isConnection(notShowMsg: Boolean): Boolean {
        val connectivityManager = App.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo =
            Objects.requireNonNull(connectivityManager)
                .activeNetworkInfo
        val isNetwork =
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        if (!isNetwork && !notShowMsg) {
            showToast(
                App.INSTANCE.getResources()
                    .getString(R.string.msg_network_connection)
            )
        }
        return isNetwork
    }

    /** Method to Get deviceId **/
    fun getDeviceId(): String? {
        return Settings.Secure.getString(
            INSTANCE.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    /**Method for getting fileType**/
    fun getFileType(url: String?): String? {
        val path = Uri.fromFile(File(url))
        var extension = MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase()
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return extension
    }

    fun getMediaList(b: Boolean): List<Media> {
        val storageBottomList: MutableList<Media> =
            ArrayList()

        val s0 = Media()
        s0.name = "Camera"
        s0.id = 0
        storageBottomList.add(s0)

        val s1 = Media()
        s1.name = "Gallery"
        s1.id = 1
        storageBottomList.add(s1)

        return storageBottomList

    }

    fun getAppDirectory(): File? {
        // External sdcard file location
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory(),
            INSTANCE.getString(R.string.app_name)
        )
        // Create storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return mediaStorageDir
    }


    fun getColor(color: Int): Int {
        return ContextCompat.getColor(INSTANCE, color)
    }


    fun deviceId(): String {
        return Settings.Secure.getString(
            INSTANCE.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun setBgView(layout: View, drawableId: Int) {
        layout.background = ContextCompat.getDrawable(
            INSTANCE,
            drawableId
        )
    }

    fun isValidEmail(email: String?): Boolean {
        val EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_REGEX)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun getRupee(price: Float): String {
        return "\u20B9${price}"
    }

    fun getItems(item: Int): String {
        if (item == 1) {
            return "${item} Item"
        }
        return "${item} Items"
    }

    fun getDeviceOS(): String {
        var codeName = "UNKNOWN"
        try {
            val fields = Build.VERSION_CODES::class.java.fields

            fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
                .forEach { codeName = it.name }
        } catch (e: Exception) {

        }
        return codeName
    }

    fun getMimeType(url: String?): String {
        val path = Uri.fromFile(File(url))
        val type_map = MimeTypeMap.getSingleton()
        var extension = MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase(Locale.ROOT)
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return type_map.getMimeTypeFromExtension(extension).toString()
    }

    fun getFullName(firstName: String?, lastName: String?): String {
        var name1 = "NA"
        var name2: String? = ""
        if (!TextUtils.isEmpty(firstName)) {
            name1 = "$firstName "
        }
        if (!TextUtils.isEmpty(lastName)) {
            name2 = lastName
        }
        return name1 + "" + name2
    }

    fun setBgTint(view: View?, color: Int) {
        view?.let {
            ViewCompat.setBackgroundTintList(
                it,
                ColorStateList.valueOf(color)
            )
        }
    }

    fun setTint(view: AppCompatImageView?, color: Int) {
        view?.setColorFilter(color)
    }
}