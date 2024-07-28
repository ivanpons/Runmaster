@file:OptIn(ExperimentalCoroutinesApi::class)

package com.llimapons.run.domain

import com.llimapons.core.domain.Timer
import com.llimapons.core.domain.location.LocationTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope
) {

    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    private val isTracking = MutableStateFlow(false)
    private val isObservingObserver = MutableStateFlow(false)

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    val currentLocation = isObservingObserver
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation){
                locationObserver.observerLocation(1000L)
            } else flowOf()
        }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            null
        )

    init {
        isTracking
            .flatMapLatest { isTracking ->
                if (isTracking){
                    Timer.timerAndEmit()
                } else flowOf()
            }
            .onEach {
                _elapsedTime.value += it
            }
            .launchIn(applicationScope)

        currentLocation
            .filterNotNull()
            .combineTransform(isTracking){ location, isTracking ->
                if (isTracking){
                    emit(location)
                }
            }
            .zip(_elapsedTime){ location, elapsedTime ->
                LocationTimestamp(
                    location = location,
                    durationTimestamp = elapsedTime
                )
            }
            .onEach { location ->
                val currentLocation = runData.value.locations
                val lastLocationsList = if(currentLocation.isNotEmpty()){
                    currentLocation.last() + location
                }else{
                    listOf(location)
                }
                val newLocationList = currentLocation.replaceLast(lastLocationsList)

                val distanceMeters = LocationDataCalculator.getTotalDistanceMeters(newLocationList)

                val distanceKm = distanceMeters / 1000.0
                val currentDuration = location.durationTimestamp

                val avgSecondsPerKm = if(distanceKm == 0.0){
                    0
                } else {
                    (currentDuration.inWholeSeconds/distanceKm).roundToInt()
                }

                _runData.update {
                    RunData(
                        distanceMeters = distanceMeters,
                        pace = avgSecondsPerKm.seconds,
                        locations = newLocationList
                    )
                }
            }.launchIn(applicationScope)
    }

    fun setIsTracking(isTracking: Boolean){
        this.isTracking.value = isTracking
    }

    fun startObservingLocation(){
        isObservingObserver.value = true
    }

    fun stopObservingLocation(){
        isObservingObserver.value = false
    }


}

private fun <T> List<List<T>>.replaceLast(replacement: List<T>): List<List<T>> {
    if(this.isEmpty()){
        return listOf(replacement)
    }
    return this.dropLast(1) + listOf(replacement)
}