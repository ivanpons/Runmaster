package com.llimapons.auth.data

import com.llimapons.auth.domain.AuthRepository
import com.llimapons.core.data.networking.post
import com.llimapons.core.domain.AuthInfo
import com.llimapons.core.domain.SessionStorage
import com.llimapons.core.domain.util.DataError
import com.llimapons.core.domain.util.EmptyResult
import com.llimapons.core.domain.util.Result
import com.llimapons.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
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

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if(result is Result.Success){
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }
}