package com.aperezs.tdd_architectcoders.validators

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CredentialsValidatorTest {

    private val validator = CredentialsValidator()

    @Test
    fun `Validate should return true when input is not null`() {
        val input = "password"
        assertTrue(validator.validate(input))
    }

    @Test
    fun `Validate should return true when input is not empty`() {
        val input = "678901"
        assertTrue(validator.validate(input))
    }

    @Test
    fun `Validate should return false when input is null`() {
        val input: String? = null
        assertFalse(validator.validate(input))
    }

    @Test
    fun `Validate should return true when input is longer or equal than six characters`() {
        val input = "123456"
        assertTrue(validator.validate(input))
    }

    @Test
    fun `Validate should return false when input is lesser than six characters`() {
        val input = "12345"
        assertFalse(validator.validate(input))
    }

}