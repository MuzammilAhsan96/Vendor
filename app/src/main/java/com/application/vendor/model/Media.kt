package com.application.vendor.model

import java.io.Serializable

class Media : Serializable {
    var position = 0
    var id = 0
    var image: String? = null
    var name: String? = null

    var type //  I = Image, V = Video
            : String? = null

    var mediaUri : String? = null
    var fileSize : Int =0
    var mimeType : String? = null
    var typeFile : Int? = null
    var from: Int? = 0 // url , 1 file


    /// for food ///
    var _id: String? = null
    var url: String? = null

}