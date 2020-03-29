package com.aperezs.tdd_architectcoders.data

import com.aperezs.tdd_architectcoders.domain.model.UserParams
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class LoginRepositoryTest {

    private val loginRepository = LoginRepository()

    @Test
    fun `When user params password is passwordValid should call success functions`() {
        //Given
        val userParams = UserParams("username", "passwordValid")
        val successFunctionMock: (String) -> Unit = mock()
        val errorFunctionMock: () -> Unit = mock()

        //When
        loginRepository.doLogin(userParams, successFunctionMock, errorFunctionMock)

        //Then
        verify(successFunctionMock).invoke("token")
    }

    @Test
    fun `When use params password is not passwordValid should call failure function`() {
        //Given
        val userParams = UserParams("username", "passwordInvalid")
        val successFunctionMock: (String) -> Unit = mock()
        val errorFunctionMock: () -> Unit = mock()

        //When
        loginRepository.doLogin(userParams, successFunctionMock, errorFunctionMock)

        //Then
        verify(errorFunctionMock).invoke()
    }
}