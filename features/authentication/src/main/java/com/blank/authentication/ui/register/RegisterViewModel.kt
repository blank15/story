package com.blank.authentication.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.domain.model.Resource
import com.blank.domain.repository.AuthRepository
import com.blank.model.auth.LoginResponseResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private var _loginResponse = MutableLiveData<Resource<LoginResponseResult>>()
    val loginResponse: LiveData<Resource<LoginResponseResult>> get() = _loginResponse
    private var _registerResponse = MutableLiveData<Resource<Any>>()
    val registerResponse: LiveData<Resource<Any>> get() = _registerResponse


    private fun login(email: String, password: String) {
       viewModelScope.launch {
           authRepository.login(email, password)
               .onEach {
                   _loginResponse.value = it
               }.collect()
       }
    }

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            authRepository.register(email, password, name)
                .onEach {
                    if (it.status == Resource.Status.SUCCESS) {
                        login(email, password)
                    }
                    _registerResponse.value = it
                }.collect()
        }
    }
}