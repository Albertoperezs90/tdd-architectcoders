package com.aperezs.tdd_architectcoders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aperezs.tdd_architectcoders.domain.DoLoginUseCase
import com.aperezs.tdd_architectcoders.presentation.LoginViewModel
import com.aperezs.tdd_architectcoders.validators.CredentialsValidator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val doLoginUseCase: DoLoginUseCase = mock()
    private val credentialsValidator: CredentialsValidator = mock()
    private val loginViewModel: LoginViewModel = LoginViewModel(credentialsValidator, doLoginUseCase)

    @Test
    fun `Username should be null when viewmodel is initialized`() {
        assertNull(loginViewModel.username.value)
    }

    @Test
    fun `Password should be null when viewmodel is initialized`() {
        assertNull(loginViewModel.password.value)
    }

    @Test
    fun `Do Login should call credentials validator`() {
        loginViewModel.doLogin()

        verify(credentialsValidator).validate(loginViewModel.password.value)
    }

    @Test
    fun `Do Login should call login use case when credentials validator returns true`() {
        loginViewModel.username.value = "username"
        loginViewModel.password.value = "password"
        whenever(credentialsValidator.validate(loginViewModel.password.value)).thenReturn(true)

        loginViewModel.doLogin()

        verify(doLoginUseCase).doLogin(any(), any(), any())
    }

    @Test
    fun `Do Login should not call login use case when credentials validator returns false`() {
        whenever(credentialsValidator.validate(loginViewModel.password.value)).thenReturn(false)

        loginViewModel.doLogin()

        verify(doLoginUseCase, times(0)).doLogin(any(), any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `Do Login should set isLoginValid to true when use case is success`() {
        //Given
        whenever(credentialsValidator.validate(loginViewModel.password.value)).thenReturn(true)
        whenever(doLoginUseCase.doLogin(any(), any(), any())).thenAnswer {
            (it.arguments[1] as (String) -> Unit).invoke("token")
        }

        //When
        loginViewModel.doLogin()

        //Then
        assertTrue(loginViewModel.isLoginValid.value!!)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `Do Login should set isLoginValid to false when use case is error`() {
        //Given
        whenever(credentialsValidator.validate(loginViewModel.password.value)).thenReturn(true)
        whenever(doLoginUseCase.doLogin(any(), any(), any())).thenAnswer {
            (it.arguments[2] as () -> Unit).invoke()
        }

        //When
        loginViewModel.doLogin()

        //Then
        assertFalse(loginViewModel.isLoginValid.value!!)
    }


}