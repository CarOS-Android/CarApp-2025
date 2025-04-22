package com.thoughtworks.carapp.ui.main.viewmodel

import android.app.Application
import android.car.VehicleIgnitionState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.main.Toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carService: CarService // 通过构造函数注入
) : ViewModel() {
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