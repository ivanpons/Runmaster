package com.llimapons.auth.domain

import com.llimapons.core.domain.util.DataError
import com.llimapons.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}