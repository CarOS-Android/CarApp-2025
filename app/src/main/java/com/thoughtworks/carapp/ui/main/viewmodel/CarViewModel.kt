package com.thoughtworks.carapp.ui.main.viewmodel

import android.car.VehicleAreaType
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
    // 车辆引擎状态
    private val _engineState = MutableStateFlow(Toggle.Off)
    val engineState: StateFlow<Toggle> = _engineState.asStateFlow()
    // 自动驻车
    private val _autoHoldState = MutableStateFlow(Toggle.Off)
    val autoHoldState: StateFlow<Toggle> = _autoHoldState.asStateFlow()
    // 刹车状态
    private val _parkingBrakeOnState = MutableStateFlow(Toggle.Off)
    val parkingBrakeOnState: StateFlow<Toggle> = _parkingBrakeOnState.asStateFlow()
    // 远光灯
    private val _highBeamState = MutableStateFlow(Toggle.Off)
    val highBeamState: StateFlow<Toggle> = _highBeamState.asStateFlow()
    // 危险信号灯-示宽灯
    private val _hazardLightsState = MutableStateFlow(Toggle.Off)
    val hazardLightsState: StateFlow<Toggle> = _hazardLightsState.asStateFlow()
    // 车前灯-近光灯
    private val _headLightsState = MutableStateFlow(Toggle.Off)
    val headLightsState: StateFlow<Toggle> = _headLightsState.asStateFlow()

    private var propertyCallbacks: List<CarService.PropertyCallback> = mutableListOf()

    init {
        connectToCar()
    }

    private fun connectToCar() {
        this.propertyCallbacks = listOf(
            CarService.PropertyCallback(VehiclePropertyIds.IGNITION_STATE, { value ->
                _engineState.value = if (value == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
            }),
            CarService.PropertyCallback(VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY, { value ->
                _autoHoldState.value = if (value as? Boolean == true) Toggle.On else Toggle.Off
            }),
            CarService.PropertyCallback(VehiclePropertyIds.PARKING_BRAKE_ON, { value ->
                _parkingBrakeOnState.value = if (value as? Boolean == true) Toggle.On else Toggle.Off
            }),
            CarService.PropertyCallback(VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH, { value ->
                _highBeamState.value = if (value == 1) Toggle.On else Toggle.Off
            }),
            CarService.PropertyCallback(VehiclePropertyIds.HAZARD_LIGHTS_SWITCH, { value ->
                _hazardLightsState.value = if (value == 1) Toggle.On else Toggle.Off
            }),
            CarService.PropertyCallback(VehiclePropertyIds.HEADLIGHTS_SWITCH, { value ->
                _headLightsState.value = if (value == 1) Toggle.On else Toggle.Off
            }),
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }

    private fun setHighBeamLights(value: Int) {
        carService.setProperty(Int::class.java, VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, value)
    }

    private fun setHazardLights(value: Int) {
        carService.setProperty(Int::class.java, VehiclePropertyIds.HAZARD_LIGHTS_SWITCH, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, value)
    }

    private fun setHeadLights(value: Int) {
        carService.setProperty(Int::class.java, VehiclePropertyIds.HEADLIGHTS_SWITCH, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, value)
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterPropertyListeners(this.propertyCallbacks)
    }
}