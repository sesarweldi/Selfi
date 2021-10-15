package com.selfi.models.response

import com.google.gson.annotations.SerializedName

data class DataResponseModel<T>(
    val success: Boolean,
    val message: String,
    @SerializedName("data")
    var data: T? = null
)