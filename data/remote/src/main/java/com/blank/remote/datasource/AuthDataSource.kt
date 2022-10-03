package com.blank.remote.datasource

import com.blank.model.BaseResponse
import com.blank.model.auth.LoginResponse
import com.blank.remote.api.AuthService
import retrofit2.Response
import java.util.HashMap

class AuthDataSource(private val authService: AuthService) {
    suspend fun login(email:String,password:String) :Response<LoginResponse> {
        val queryParam = HashMap<String, String>()
        queryParam["email"] = email
        queryParam["password"] = password

        return authService.login(queryParam)
    }

    suspend fun register(email:String,password:String,name:String) :Response<BaseResponse> {
        val queryParam = HashMap<String, String>()
        queryParam["email"] = email
        queryParam["password"] = password
        queryParam["name"] = name

        return authService.register(queryParam)
    }

}