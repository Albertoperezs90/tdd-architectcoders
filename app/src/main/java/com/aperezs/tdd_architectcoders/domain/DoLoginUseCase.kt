package com.aperezs.tdd_architectcoders.domain

import com.aperezs.tdd_architectcoders.data.LoginRepository
import com.aperezs.tdd_architectcoders.domain.model.UserParams

class DoLoginUseCase(private val loginRepository: LoginRepository) {

    fun doLogin(userParams: UserParams, success: (String) -> Unit, error: () -> Unit) {
        loginRepository.doLogin(userParams, success = { token ->
            success(token)
        }, failure = {
            error()
        })
    }

}
