package com.selfi.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.selfi.models.User

class ResponseLogin (
    val success: Boolean? = null,
    val message: String,
    @SerializedName("data")
    val user: User? = null
)