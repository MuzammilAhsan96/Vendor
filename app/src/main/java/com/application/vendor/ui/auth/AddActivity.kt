package com.application.vendor.ui.auth

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.application.vendor.R
import com.application.vendor.base.App
import com.application.vendor.base.BaseActivity
import com.application.vendor.model.Media
import com.application.vendor.utils.AppConstant
import com.application.vendor.utils.AppConstant.IMAGE_PICK_CODE
import com.application.vendor.utils.AppConstant.REQUEST_CODE
import com.application.vendor.utils.AppUtil
import com.application.vendor.utils.PreferenceKeeper
import com.application.vendor.viewModel.AuthViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.btnLogin
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import android.provider.MediaStore.Images
import java.io.ByteArrayOutputStream


class AddActivity : BaseActivity(), BottomSheetFragment.cameraGallery {
    val bottomSheetFragment = BottomSheetFragment(this@AddActivity)
    private var selectedMediaFiles: MutableList<Media>? = ArrayList()
    private val viewModel: AuthViewModel by viewModels()
    var count = 3

    override fun layoutRes(): Int {
        return R.layout.activity_add
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backbtn.setOnClickListener {
            finish()
        }
        ivAddImage.setOnClickListener {
            validatePermission()
        }
        setListener()
        setObservables()
        deleteImage()
    }

    private fun deleteImage() {
        ivDelete1.setOnClickListener {
            ivPhoto1.setImageURI(null)
            root1.visibility = View.GONE
            (selectedMediaFiles?.size)?.minus(1)?.let { it1 -> selectedMediaFiles?.removeAt(it1) }
            println(selectedMediaFiles?.size)
            count++
        }
        ivDelete2.setOnClickListener {
            ivPhoto2.setImageURI(null);
            root2.visibility = View.GONE
            (selectedMediaFiles?.size)?.minus(1)?.let { it1 -> selectedMediaFiles?.removeAt(it1) }
            count++
            println(selectedMediaFiles?.size)
        }
        ivDelete3.setOnClickListener {
            ivPhoto3.setImageURI(null);
            root3.visibility = View.GONE
            (selectedMediaFiles?.size)?.minus(1)?.let { it1 -> selectedMediaFiles?.removeAt(it1) }
            count++
            println(selectedMediaFiles?.size)
        }

    }

    private fun setListener() {
        btnLogin.setOnClickListener {
            addFoodImageUploadAPI()
        }
    }

    private fun addFoodImageUploadAPI() {
        if (AppUtil.isConnection()) {
            viewModel.addFoodImageUploadAPI(selectedMediaFiles)
        }
    }


    private fun setObservables() {

        viewModel.imageUploadSuccess.observe(this) { data ->
            AppUtil.showToast(data?.message)
            hideProgressBar()
            PreferenceKeeper.getInstance().isLogin = true


        }

        viewModel.error.observe(this) { errors ->
            hideProgressBar()
            AppUtil.showToast(errors.errorMessage)
        }
    }

    private fun validatePermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {

                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(this@AddActivity, "Permission Granted", Toast.LENGTH_LONG).show()


                    bottomSheetFragment.show(supportFragmentManager, "bottomsheetdialog")
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@AddActivity,
                        R.string.storage_permission_denied_message,
                        Toast.LENGTH_LONG
                    ).show()
                    bottomSheetFragment.dismiss()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@AddActivity)
                        .setTitle(R.string.storage_permission_rationale_title)
                        .setMessage(R.string.storage_permission_rationale_message)
                        .setNegativeButton(
                            android.R.string.cancel,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                token?.cancelPermissionRequest()
                            })
                        .setPositiveButton(
                            android.R.string.ok,
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                                token?.continuePermissionRequest()
                            })
                        .show()
                }

            }
            ).check()

    }

    //get file path from storage
    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            App.INSTANCE.getContentResolver().query(uri, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULL POINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(column_index)
            cursor.close()
            filePath
        } else null
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AppConstant.IMAGE_PICK_CODE)
    }

    private fun pickImageFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    AppUtil.showToast("Permission denied")
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var tempUri: Uri? = null
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null)
        {
            tempUri = getImageUri(applicationContext, data.extras?.get("data") as Bitmap)
        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            tempUri = data?.data
        }
        if (count == 3) {
            root1.visibility = View.VISIBLE
            ivPhoto1.setImageURI(tempUri)
        } else if (count == 2) {
            root2.visibility = View.VISIBLE
            ivPhoto2.setImageURI(tempUri)
        } else if (count == 1) {
            root3.visibility = View.VISIBLE
            ivPhoto3.setImageURI(tempUri)
        } else {
            AppUtil.showToast("Maximum 3 Images can be captured!")
        }

        val media = Media()
        if (tempUri != null) {
            println("path = "+tempUri.path)
        }
        println("path2 = "+tempUri?.let { getPath(it) })
        media.image = tempUri?.let { getPath(it) }
        selectedMediaFiles?.add(media)
        count--

    }

    override fun onclick(v: Int) {
        if (v == 1) {
            pickImageFromCamera()
        } else {
            pickImageFromGallery()
        }
        bottomSheetFragment.dismiss()
    }

}

