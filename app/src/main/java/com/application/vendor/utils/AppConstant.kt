package com.application.vendor.utils

import com.application.vendor.model.api.user.UserProfile

object AppConstant {

    const val DEVICE_TYPE = 1
    const val IMAGE_PICK_CODE = 1000
    const val PERMISSION_CODE = 1001
    const val REQUEST_CODE=200
    const val SPLASH_DELAY: Long = 2000
    const val DETECT_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    var USER: UserProfile? = UserProfile()

    interface BK {
        companion object {
            const val TYPE = "type"
            const val ODER_ID = "ODER_ID"
            const val ODER_FROM = "ODER_FROM"
            const val PAYMENT = "PAYMENT"
        }
    }
    interface TYPE {
        companion object {
            const val CAMERA_IMAGE = 0
            const val GALLERY_IMAGE = 1
            const val CAMERA_VIDEO = 2
            const val GALLERY_VIDEO = 3
            const val DOCS = 4
            const val AUDIO = 5
        }
    }

    interface PKN {
        companion object {
            const val ACCESS_TOKEN = "access_token"
        }
    }

    interface MT {
        companion object {
            const val UPLOAD_MEDIA = "images"
            const val TEXT_PLAIN = "text/plain"
        }
    }
    interface REQUEST_CODES {
        companion object {
            const val APP_SETTING = 1109
            const val CROP_VIDEO = 2200
            const val CROP_IMAGE_REQ_CODE = 1545
            const val MULTIPLE_IMAGE_VIDEOS_GALLERY_REQUEST_CODE = 7503
            const val LOCATION_REQ_CODE = 7504
            const val REQ_COUNTRY_CODE = 7505
            const val REQ_STATE_CODE = 7506
            const val REQ_ISSUE_CODE = 7507
            const val ADD_CARD = 7508
            const val REQ_BUY_NOW_CODE = 8000
            const val REQ_ADD_CARD_CODE = 8001
            const val RATING = 122
            const val REQ_WRITE_REVIEWS = 1001
            const val LOCATION_SETTINGS_REQ = 9000
            const val LOCATION_PERMISSION_REQ = 9001
            const val MAP_SETTINGS = 9002
            const val MAP_DETAIL_ANNOUNCEMNT_EVENT = 9003
            const val TRAILHEAD = 9004
            const val TRAILS = 9005

            const val CAMERA_IMAGE = 9008
            const val GALLERY_IMAGE = 9010
            const val CAMERA_VIDEO = 9009
            const val GALLERY_VIDEO = 9011


            const val Add_WAY_POINT = 9012


            const val PICK_VIDEO_FROM_GALLERY = 1003
            const val TRIM_VIDEO_ACTIVITY = 1006
            const val PREVIEW_VIDEO = 1007
            const val REQUEST_CODE_BACKGROUND = 9013

            const val DOCS_CAPTURE = 9015
            const val COMMENT_SCREEN = 9016


        }
    }
}