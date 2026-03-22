package com.llimapons.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer {

    fun timerAndEmit() : Flow<Duration> {
        return flow {
            var lastEmmitTime = System.currentTimeMillis()
            while (true){
                delay(200)
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastEmmitTime
                emit(elapsedTime.milliseconds)
                lastEmmitTime = currentTime
            }
        }
    }
}