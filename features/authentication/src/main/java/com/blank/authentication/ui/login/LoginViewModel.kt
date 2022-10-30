package com.blank.authentication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.domain.model.Resource
import com.blank.domain.repository.AuthRepository
import com.blank.model.auth.LoginResponseResult
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private var _loginResponse = MutableLiveData<Resource<LoginResponseResult>>()
    val loginResponse: LiveData<Resource<LoginResponseResult>> get() = _loginResponse
    fun login(email: String, password: String) {
      viewModelScope.launch {
          authRepository.login(email, password)
              .cancellable()
              .onEach {
                  _loginResponse.value = it
              }.collect()
      }
    }
}