package com.aperezs.tdd_architectcoders.validators

class CredentialsValidator {

    fun validate(input: String?): Boolean {
        return input?.length ?: 0 >= 6
    }

}
