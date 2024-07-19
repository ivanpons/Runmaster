@file:OptIn(ExperimentalFoundationApi::class)

package com.llimapons.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.llimapons.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val channelEvent = Channel<LoginEvent>()
    val event = channelEvent.receiveAsFlow()

    fun onAction(action: LoginAction) {

    }


}