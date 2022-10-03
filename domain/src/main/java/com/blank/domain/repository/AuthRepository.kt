package com.blank.domain.repository

import com.blank.domain.model.Resource
import com.blank.model.auth.LoginResponseResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    var isLogin:Boolean
    fun login(email:String,password:String) : Flow<Resource<LoginResponseResult>>
    fun register(email:String,password:String,name:String) : Flow<Resource<Any>>
}