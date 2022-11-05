package com.blank.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.blank.domain.repository.AuthRepository
import com.blank.model.BaseResponse
import com.blank.model.auth.LoginResponse
import com.blank.model.auth.LoginResponseResult
import com.blank.remote.datasource.AuthDataSource
import com.blank.repository.util.SharedPreferenceHelper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class AuthRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val testDispatchers = UnconfinedTestDispatcher()
    val sharedPreferenceHelper = mockk<SharedPreferenceHelper>(relaxed = true)
    val authDataSource = mockk<AuthDataSource>(relaxed = true)
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatchers)
        authRepository = spyk(AuthRepositoryImpl(sharedPreferenceHelper, authDataSource))
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when login should return success`() = runTest {
        // given
        val email = "cobatest@gmail.com"
        val password = "123456"
        val dataLogin = LoginResponseResult("123", "name", "Aizasda")
        val response = LoginResponse(false, "success", dataLogin)
        val result = Response.success(response)

        // when
        coEvery {
            authDataSource.login(email, password)
        } returns result

        val callData = authRepository.login(email, password)

        Assert.assertEquals(dataLogin, callData.last().data)
    }

    @Test
    fun `when register should return success`() = runTest {
        // given
        val email = "cobatest@gmail.com"
        val password = "123456"
        val name = "cobatest"
        val result = Response.success(BaseResponse(false, "success"))

        // when
        coEvery {
            authDataSource.register(email, password, name)
        } returns result

        val callData = authRepository.register(email, password, name)

        Assert.assertEquals(result.message(), callData.last().message)
    }
}
