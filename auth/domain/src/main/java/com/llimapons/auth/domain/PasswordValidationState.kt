package com.llimapons.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasNumber: Boolean = false,

    ) {

    val isValidPassword: Boolean
        get() = hasMinLength && hasUpperCaseCharacter && hasLowerCaseCharacter && hasNumber
}