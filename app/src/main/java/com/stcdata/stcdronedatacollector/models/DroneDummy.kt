@file:OptIn(ExperimentalTime::class)

package com.stcdata.stcdronedatacollector.models

import android.app.Application
import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime


fun randomNumber(
    zeroProbability: Double = 0.6,
    positiveRangeProbability: Double = 0.2,
    negativeRangeProbability: Double = 0.2,
    minPositiveRange: Double = 1.5,
    maxPositiveRange: Double = 8.0,
    minNegativeRange: Double = -8.0,
    maxNegativeRange: Double = -1.5
): Double {
    val random = kotlin.random.Random.nextDouble()
    return when {
        random < zeroProbability -> 0.0
        random < zeroProbability + positiveRangeProbability -> minPositiveRange + ((maxPositiveRange - minPositiveRange) * kotlin.random.Random.nextDouble())
        else -> minNegativeRange + ((maxNegativeRange - minNegativeRange) * kotlin.random.Random.nextDouble())
    }
}


fun randomDuration(from: Long, to: Long): Long {
    return Random.nextLong(to - from +1) +from
}

fun randomVerticalSpeedFlow() : Flow<Double>  = flow {
    while (true) {
        emit(randomNumber())
        delay(randomDuration(5_000, 9_000))
    }
}

fun randomHorizontalSpeedFlow() : Flow<Double>  = flow {
    while (true) {
        val minRange = 0.0003
        val maxRange = 0.0007
        emit(randomNumber(
            zeroProbability = 0.2,
            positiveRangeProbability = 0.8,
            negativeRangeProbability = 0.0,
            minPositiveRange = minRange,
            maxPositiveRange = maxRange,
            minNegativeRange = -minRange,
            maxNegativeRange = -maxRange
        ))
        delay(randomDuration(5_000, 9_000))
    }
}

fun modifyGpsCoordinates(
    latitude: Double,
    longitude: Double,
    heading: Double,
    distance: Double
): Pair<Double, Double> {
    val deltaLatitude = distance * sin(Math.toRadians(heading))
    val deltaLongitude = distance * cos(Math.toRadians(heading)) / cos(Math.toRadians(latitude))
    return Pair(latitude + deltaLatitude, longitude + deltaLongitude)
}



data class DroneState(
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val heading: Double = 0.0,
    val altitude: Double = 0.0,
    val speed: Float = 0f,
    val roll: Double = 0.0,
    val pitch: Double = 0.0,
    val battery: Float = 0f,

    ) {


    fun location(): Location {
        val location = Location("DroneState")
        location.longitude = longitude
        location.latitude = latitude
        return location
    }
}

class Drone {


    fun randomHeading() : Flow<Double>  = flow {
        while (true) {
            emit(Random.nextDouble())
            delay(randomDuration(5_000, 9_000))
        }
    }




    fun droneStateFlow() : Flow<DroneState> = flow {
        delay(600)
        var state = DroneState(
            pitch = 72.0,
            altitude = 3000.0,
            latitude = 50.45,
            longitude = 30.523333)
        val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
        var headingFlow = randomHeading()

        var verticalSpeed = 0.0
        var verticalSpeedFlow = randomVerticalSpeedFlow()

        var horizontalSpeed = 0.0
        var horizontalSpeedFlow = randomHorizontalSpeedFlow()

        scope.launch {
            headingFlow.collect { h -> state = state.copy(heading = h) }
        }
        scope.launch {
            verticalSpeedFlow.collect { v -> verticalSpeed = v }
        }
        scope.launch {
           horizontalSpeedFlow.collect { h -> horizontalSpeed = h }
        }

        while (true) {
            val (latitude, longitude) = modifyGpsCoordinates(state.latitude, state.longitude, state.heading, horizontalSpeed)
            state = state.copy(
                latitude = latitude,
                longitude = longitude,
                altitude = state.altitude + verticalSpeed)
            println("h $horizontalSpeed v $verticalSpeed  $state")
            emit(state)
            delay(stateUpdateDuration.seconds)
        }
    }

    companion object {
        val stateUpdateDuration: Double = 0.3
    }


}