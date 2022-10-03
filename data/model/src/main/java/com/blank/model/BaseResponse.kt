package com.blank.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
)
