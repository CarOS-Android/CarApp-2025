package com.thoughtworks.carapp.ui.main.viewmodel

import android.car.VehicleIgnitionState
import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.main.Toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carService: CarService // 通过构造函数注入
) : ViewModel() {
    private val _engineState = MutableStateFlow(Toggle.Off)
    val engineState: StateFlow<Toggle> = _engineState.asStateFlow()

    private val _autoHoldState = MutableStateFlow(Toggle.Off)
    val autoHoldState: StateFlow<Toggle> = _autoHoldState.asStateFlow()

    init {
        connectToCar()
    }

    private fun connectToCar() {
        carService.registerIgnitionStateListener { state ->
            _engineState.value = if (state == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
        }
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterIgnitionStateListener()
    }
} 