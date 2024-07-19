@file:OptIn(ExperimentalFoundationApi::class)

package com.llimapons.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llimapons.auth.domain.AuthRepository
import com.llimapons.auth.domain.UserDataValidator
import com.llimapons.auth.presentation.R
import com.llimapons.core.domain.util.DataError
import com.llimapons.core.domain.util.Result
import com.llimapons.core.presentation.ui.UiText
import com.llimapons.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val channelEvent = Channel<LoginEvent>()
    val event = channelEvent.receiveAsFlow()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email.toString().trim()
                ) && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoginIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoginIn = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        channelEvent.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    }else{
                        channelEvent.send(
                            LoginEvent.Error(result.error.asUiText())
                        )
                    }
                }
                is Result.Success -> channelEvent.send(LoginEvent.LoginSuccess)
            }
        }
    }
}