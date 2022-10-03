package com.blank.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseResult(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)