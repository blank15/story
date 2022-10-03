package com.blank.model

import com.google.gson.annotations.SerializedName

data class ErrorResponseRetrofit(

    @SerializedName("message")
    var message: String? = "",
)