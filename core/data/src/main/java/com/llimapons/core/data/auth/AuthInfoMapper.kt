package com.llimapons.core.data.auth

import com.llimapons.core.domain.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable =
    AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )

fun AuthInfoSerializable.toAuthInfo(): AuthInfo =
    AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )