package com.blank.authentication

import androidx.lifecycle.Observer
import com.blank.authentication.ui.register.RegisterViewModel
import com.blank.domain.model.ErrorResponse
import com.blank.domain.model.Resource
import com.blank.model.auth.LoginResponseResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.junit.Test

class RegisterViewModelTest : BaseTest() {
    lateinit var registerViewModel: RegisterViewModel
    var loginResponseObserver = mockk<Observer<Resource<LoginResponseResult>>>(relaxed = true)
    val registerResponseObserver = mockk<Observer<Resource<Any>>>(relaxed = true)
    override fun setUp() {
        super.setUp()
        registerViewModel = spyk(RegisterViewModel(authRepositoru)).apply {
            loginResponse.observeForever(loginResponseObserver)
            registerResponse.observeForever(registerResponseObserver)
        }
    }

    @Test
    fun `When register should success`() {
        //given
        val email = "cobatest@gmail.com"
        val password = "123456"
        val name = "coba"
        val result = Resource.success(Any())

        val response = LoginResponseResult("123", "name", "Aizasda")
        val resultLogin = Resource.success(response)
        //when
        coEvery {
            authRepositoru.register(email, password, name)
        } returns flow {
            emit(result)
        }
        coEvery {
            authRepositoru.login(email, password)
        } returns flow {
            emit(resultLogin)
        }
        registerViewModel.register(email, password, name)

        //result
        verifyOrder {
            registerResponseObserver.onChanged(result)
        }
        verifyOrder {
            loginResponseObserver.onChanged(resultLogin)
        }

    }

    @Test
    fun `When register but return failed`() {
        //given
        val email = "cobatest@gmail.com"
        val password = "123456"
        val name = "coba"
        val result = Resource.error(ErrorResponse(true, "email telah terdaftar"), null)

        //when
        coEvery {
            authRepositoru.register(email, password, name)
        } returns flow {
            emit(result)
        }
        registerViewModel.register(email, password, name)

        //result
        verifyOrder {
            registerResponseObserver.onChanged(result)
        }


    }

}