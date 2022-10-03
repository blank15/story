package com.blank.repository

import com.blank.domain.model.Resource
import com.blank.domain.repository.AuthRepository
import com.blank.domain.util.StoryKey.KEY_LOGIN
import com.blank.domain.util.StoryKey.TOKEN
import com.blank.model.BaseResponse
import com.blank.model.auth.LoginResponse
import com.blank.model.auth.LoginResponseResult
import com.blank.remote.datasource.AuthDataSource
import com.blank.repository.util.NetworkHandling
import com.blank.repository.util.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AuthRepositoryImpl(
    private val sharedPref: SharedPreferenceHelper,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override var isLogin: Boolean
        get() = sharedPref.getSharedPreferences(KEY_LOGIN,false)
        set(value) {
            sharedPref.setPreferences(KEY_LOGIN,value)
        }

    override fun login(email: String, password: String): Flow<Resource<LoginResponseResult>> {
        return object : NetworkHandling<LoginResponseResult, LoginResponse>() {
            override fun processResponse(response: LoginResponse?): LoginResponseResult? {
                response?.let { response ->
                    isLogin = !response.error
                   response.loginResponseResult?.let {
                       sharedPref.setPreferences(TOKEN,it.token)
                   }
                }
                return response?.loginResponseResult
            }
            override suspend fun createCallAsync(): Response<LoginResponse> {
                return authDataSource.login(email,password)
            }
        }.asFlow
    }

    override fun register(email: String, password: String, name: String): Flow<Resource<Any>> {
        return object : NetworkHandling<Any,BaseResponse>() {
            override fun processResponse(response: BaseResponse?): Any? {
                return null
            }

            override suspend fun createCallAsync(): Response<BaseResponse> {
                return authDataSource.register(email, password, name)
            }

        }.asFlow
    }
}