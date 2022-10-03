package com.blank.remote.api

import com.blank.model.BaseResponse
import com.blank.model.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @FieldMap postData: Map<String, String>
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @FieldMap postData: Map<String, String>
    ): Response<BaseResponse>
}