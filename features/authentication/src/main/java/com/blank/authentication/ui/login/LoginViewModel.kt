package com.blank.authentication.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.blank.authentication.R
import kotlinx.coroutines.flow.collect
import com.blank.domain.model.Resource
import com.blank.domain.repository.AuthRepository
import com.blank.model.auth.LoginResponseResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private var _loginResponse = MutableLiveData<Resource<LoginResponseResult>>()
    val loginResponse get() = _loginResponse

    fun login(email:String,password:String){
        viewModelScope.launch {
            authRepository.login(email, password)
                .onEach {
                    _loginResponse.value = it
                }.collect()
        }
    }
}