package com.llimapons.auth.data

import com.llimapons.auth.domain.AuthRepository
import com.llimapons.core.data.networking.post
import com.llimapons.core.domain.util.DataError
import com.llimapons.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body =RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}