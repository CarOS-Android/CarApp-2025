package com.thoughtworks.carapp.ui.main

import android.app.Application
import android.car.VehicleIgnitionState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.service.CarService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {
    private val carService = CarService(application)
    private val _engineState = MutableStateFlow(Toggle.Off)
    val engineState: StateFlow<Toggle> = _engineState.asStateFlow()

    init {
        connectToCar()
    }

    private fun connectToCar() {
        carService.connect()
        carService.registerIgnitionStateListener { state ->
            viewModelScope.launch {
                _engineState.value = if (state == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterIgnitionStateListener()
        carService.disconnect()
    }
} 