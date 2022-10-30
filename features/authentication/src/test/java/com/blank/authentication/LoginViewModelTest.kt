package com.blank.authentication


import androidx.lifecycle.Observer
import com.blank.authentication.ui.login.LoginViewModel
import com.blank.domain.model.ErrorResponse
import com.blank.domain.model.Resource
import com.blank.model.auth.LoginResponseResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.junit.Test


class LoginViewModelTest : BaseTest() {
    lateinit var loginViewModel: LoginViewModel
    var loginResponseObserver = mockk<Observer<Resource<LoginResponseResult>>>(relaxed = true)
    override fun setUp() {
        super.setUp()
        loginViewModel = spyk(LoginViewModel(authRepositoru)).apply {
            loginResponse.observeForever(loginResponseObserver)
        }
    }

    @Test
    fun `When login should success`() {

        //given
        val email = "cobatest@gmail.com"
        val password = "123456"
        val response = LoginResponseResult("123", "name", "Aizasda")
        val result = Resource.success(response)

        //when
        coEvery {
            authRepositoru.login(email, password)
        } returns flow {
            emit(result)
        }
        loginViewModel.login(email, password)

        //result
        verifyOrder {
            loginResponseObserver.onChanged(result)
        }
    }

    @Test
    fun `When login return  failed`() {
        val email = "cobates12dast@gmail.com"
        val password = "123456"
        val response = ErrorResponse(true, "User not found")
        val result = Resource.error(response, null)


        coEvery {
            authRepositoru.login(email, password)
        } returns flow {
            emit(result)
        }
        loginViewModel.login(email, password)

        verifyOrder {
            loginResponseObserver.onChanged(result)
        }
    }
}