package com.thoughtworks.carapp.ui.main.viewmodel

import android.car.VehicleAreaDoor
import android.car.VehicleAreaSeat
import android.car.VehicleAreaType
import android.car.VehicleIgnitionState
import android.car.VehiclePropertyIds
import android.util.Log
import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.main.Lock
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

    // 主驾-空调温度
    private val _diverTemperature = MutableStateFlow(0.0f)
    val diverTemperature: StateFlow<Float> = _diverTemperature.asStateFlow()

    // 副驾-空调温度
    private val _coPilotTemperature = MutableStateFlow(0.0f)
    val coPilotTemperature: StateFlow<Float> = _coPilotTemperature.asStateFlow()

    // 门锁状态
    private val _carLockState = MutableStateFlow(Lock.Locked)
    val carLockState: StateFlow<Lock> = _carLockState.asStateFlow()
    private val doorAreaIds = listOf(
        VehicleAreaDoor.DOOR_ROW_1_LEFT,
        VehicleAreaDoor.DOOR_ROW_1_RIGHT,
        VehicleAreaDoor.DOOR_ROW_2_LEFT,
        VehicleAreaDoor.DOOR_ROW_2_RIGHT
    )
    private var doorStates = mutableMapOf(
        VehicleAreaDoor.DOOR_ROW_1_LEFT to Lock.Locked,
        VehicleAreaDoor.DOOR_ROW_1_RIGHT to Lock.Locked,
        VehicleAreaDoor.DOOR_ROW_2_LEFT to Lock.Locked,
        VehicleAreaDoor.DOOR_ROW_2_RIGHT to Lock.Locked,
    )

    private var propertyCallbacks: List<CarService.PropertyCallback> = listOf()

    init {
        connectToCar()
    }

    private fun connectToCar() {
        this.propertyCallbacks = listOf(
            CarService.PropertyCallback(VehiclePropertyIds.IGNITION_STATE, null) { value, _ ->
                _engineState.value = if (value == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY,
                null
            ) { value, _ ->
                _autoHoldState.value = if (value as? Boolean == true) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(VehiclePropertyIds.PARKING_BRAKE_ON, null) { value, _ ->
                _parkingBrakeOnState.value =
                    if (value as? Boolean == true) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH,
                null
            ) { value, _ ->
                _highBeamState.value = if (value == 1) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(VehiclePropertyIds.HAZARD_LIGHTS_SWITCH, null) { value, _ ->
                _hazardLightsState.value = if (value == 1) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(VehiclePropertyIds.HEADLIGHTS_SWITCH, null) { value, _ ->
                _headLightsState.value = if (value == 1) Toggle.On else Toggle.Off
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.DOOR_LOCK,
                doorAreaIds
            ) { value, areaId ->
                doorStates[areaId] = if (value as? Boolean == true) Lock.Locked else Lock.Unlocked
                _carLockState.value = doorStates.values.reduce { acc, b -> acc or b }
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.HVAC_TEMPERATURE_SET,
                listOf(VehicleAreaSeat.SEAT_ROW_1_LEFT, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                if (areaId == VehicleAreaSeat.SEAT_ROW_1_LEFT) {
                    _diverTemperature.value = value as Float
                }
                if (areaId == VehicleAreaSeat.SEAT_ROW_1_RIGHT) {
                    _coPilotTemperature.value = value as Float
                }
            }
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }

    fun setDiverTemperature(value: Float) {
        carService.setProperty(VehiclePropertyIds.HVAC_TEMPERATURE_SET, VehicleAreaSeat.SEAT_ROW_1_LEFT, value)
    }

    fun setCoPilotTemperature(value: Float) {
        carService.setProperty(VehiclePropertyIds.HVAC_TEMPERATURE_SET, VehicleAreaSeat.SEAT_ROW_1_RIGHT, value)
    }

    fun toggleHazardLights() {
        val newState = if (_hazardLightsState.value == Toggle.On) {
            Toggle.Off
        } else {
            Toggle.On
        }
        _hazardLightsState.value = newState
        setHazardLights(newState.toIntValue())
    }

    fun toggleHighBeamLights() {
        val newState = if (_highBeamState.value == Toggle.On) {
            Toggle.Off
        } else {
            Toggle.On
        }
        _highBeamState.value = newState
        setHighBeamLights(newState.toIntValue())
    }

    fun toggleHeadLights() {
        val newState = if (_headLightsState.value == Toggle.On) {
            Toggle.Off
        } else {
            Toggle.On
        }
        _headLightsState.value = newState
        setHeadLights(newState.toIntValue())
    }

    fun toggleCarLock() {
        val newValue = _carLockState.value.switch() != Lock.Unlocked
        carService.setPropertyForMultipleAreas(
            VehiclePropertyIds.DOOR_LOCK,
            doorAreaIds,
            newValue
        )
    }

    // 将 Toggle 转换为 Int
    private fun Toggle.toIntValue() = when (this) {
        Toggle.On -> 1
        Toggle.Off -> 0
    }

    private fun setHighBeamLights(value: Int) {
        carService.setProperty(
            Int::class.java,
            VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH,
            VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,
            value
        )
    }

    private fun setHazardLights(value: Int) {
        carService.setProperty(
            Int::class.java,
            VehiclePropertyIds.HAZARD_LIGHTS_SWITCH,
            VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,
            value
        )
    }

    private fun setHeadLights(value: Int) {
        carService.setProperty(
            Int::class.java,
            VehiclePropertyIds.HEADLIGHTS_SWITCH,
            VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL,
            value
        )
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterPropertyListeners(this.propertyCallbacks)
    }
}