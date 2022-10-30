package com.blank.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.blank.domain.repository.AuthRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
abstract class BaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    val testDispatchers = UnconfinedTestDispatcher()
    lateinit var authRepositoru: AuthRepository

    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatchers)
        authRepositoru = mockk()
    }

    @After
    open fun cleanUp() {
        Dispatchers.resetMain()
    }
}