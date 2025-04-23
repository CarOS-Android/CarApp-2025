package com.thoughtworks.carapp.ui.main.viewmodel

import android.car.VehicleIgnitionState
import android.car.VehiclePropertyIds
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

    private var propertyCallbacks: List<Map<Int, (Any) -> Unit>> = mutableListOf()

    init {
        connectToCar()
    }

    private fun connectToCar() {
        this.propertyCallbacks = listOf<Map<Int, (Any) -> Unit>>(
            mapOf<Int, (Any) -> Unit>(
                VehiclePropertyIds.IGNITION_STATE to { value ->
                    _engineState.value = if (value == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
                },
                VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY to { value ->
                    _autoHoldState.value = if (value as? Boolean == true) Toggle.On else Toggle.Off
                },
                VehiclePropertyIds.PARKING_BRAKE_ON to { value ->

                })
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterPropertyListeners(this.propertyCallbacks)
    }
}