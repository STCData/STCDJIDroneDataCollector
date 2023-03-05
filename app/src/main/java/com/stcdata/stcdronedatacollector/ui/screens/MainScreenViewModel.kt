package com.stcdata.stcdronedatacollector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stcdata.stcdronedatacollector.models.Drone
import com.stcdata.stcdronedatacollector.models.DroneState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



data class MainScreenUiState(
    val droneState: DroneState = DroneState(),
    val mapIsScene: Boolean = true,
    )




class MainScreenViewModel(application: Application, drone: Drone) : AndroidViewModel(application) {
    private val drone = drone
    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(MainScreenUiState())

    private val mapIsScene = MutableStateFlow(true)

    val state: StateFlow<MainScreenUiState>
        get() = _state

    init {
        viewModelScope.launch {
            // Combines the latest value from each of the flows, allowing us to generate a
            // view state instance which only contains the latest values.
            combine(
                mapIsScene,
                drone.droneStateFlow()
            ) { mapIsScene, droneState ->
                MainScreenUiState(
                    droneState = droneState,
                    mapIsScene = mapIsScene
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }

    }





    fun onTBMapMode() {
        viewModelScope.launch {
            mapIsScene.value = !mapIsScene.value
        }

    }

}


