package com.aperezs.tdd_architectcoders.data

import com.aperezs.tdd_architectcoders.domain.model.UserParams

class LoginRepository {

    fun doLogin(userParams: UserParams, success: (String) -> Unit, failure: () -> Unit) {
        if (userParams.password == "passwordValid") {
            success("token")
        } else {
            failure()
        }
    }

}
