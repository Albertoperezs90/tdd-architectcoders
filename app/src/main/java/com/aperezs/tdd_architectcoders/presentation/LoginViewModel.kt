package com.aperezs.tdd_architectcoders.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aperezs.tdd_architectcoders.domain.DoLoginUseCase
import com.aperezs.tdd_architectcoders.domain.model.UserParams
import com.aperezs.tdd_architectcoders.validators.CredentialsValidator

class LoginViewModel(private val credentialsValidator: CredentialsValidator, private val doLoginUseCase: DoLoginUseCase) : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isLoginValid = MutableLiveData<Boolean>()

    fun doLogin() {
        if (credentialsValidator.validate(password.value)) {
            doLoginUseCase.doLogin(UserParams(username.value ?: "", password.value ?: ""), {
                isLoginValid.value = true
            }, {
                isLoginValid.value = false
            })
        }
    }

}
