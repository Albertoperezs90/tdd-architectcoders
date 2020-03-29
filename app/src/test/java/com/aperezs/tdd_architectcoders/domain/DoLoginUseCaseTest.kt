package com.aperezs.tdd_architectcoders.domain

import com.aperezs.tdd_architectcoders.data.LoginRepository
import com.aperezs.tdd_architectcoders.domain.model.UserParams
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class DoLoginUseCaseTest {


    private val loginRepository: LoginRepository = mock()
    private val doLoginUseCase = DoLoginUseCase(loginRepository)

    @Test
    fun `Do Login should call repository with given params`() {
        val userParams = UserParams("username", "password")
        val successFunctionMock: (String) -> Unit = mock()
        val errorFunctionMock: () -> Unit = mock()

        doLoginUseCase.doLogin(userParams, successFunctionMock, errorFunctionMock)

        verify(loginRepository).doLogin(eq(userParams), any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun `Do Login should call given success function when repository is successful`() {
        val userParams = UserParams("username", "password")
        val successFunctionMock: (String) -> Unit = mock()
        val errorFunctionMock: () -> Unit = mock()
        whenever(loginRepository.doLogin(eq(userParams), any(), any())).thenAnswer {
            (it.arguments[1] as (String) -> Unit).invoke("token")
        }

        doLoginUseCase.doLogin(userParams, successFunctionMock, errorFunctionMock)

        verify(successFunctionMock).invoke("token")
    }


    @Suppress("UNCHECKED_CAST")
    @Test
    fun `Do Login should call given error function when repository is failure`() {
        //Given
        val userParams = UserParams("username", "password")
        val successFunctionMock: (String) -> Unit = mock()
        val errorFunction: () -> Unit = mock()
        whenever(loginRepository.doLogin(any(), any(), any())).thenAnswer {
            (it.arguments[2] as () -> Unit).invoke()
        }

        //When
        doLoginUseCase.doLogin(userParams, successFunctionMock, errorFunction)

        //Then
        verify(errorFunction).invoke()
    }


}