package com.thoughtworks.carapp.ui.main.viewmodel

import android.car.VehicleAreaDoor
import android.car.VehicleAreaSeat.SEAT_ROW_1_LEFT
import android.car.VehicleAreaSeat.SEAT_ROW_1_RIGHT
import android.car.VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL
import android.car.VehicleIgnitionState
import android.car.VehiclePropertyIds
import android.car.VehiclePropertyIds.HAZARD_LIGHTS_SWITCH
import android.car.VehiclePropertyIds.HEADLIGHTS_SWITCH
import android.car.VehiclePropertyIds.HIGH_BEAM_LIGHTS_SWITCH
import android.car.VehiclePropertyIds.HVAC_TEMPERATURE_SET
import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.main.Lock
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.components.TemperatureType
import com.thoughtworks.carapp.ui.main.presentation.CarState
import com.thoughtworks.carapp.ui.main.presentation.ViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carService: CarService // 通过构造函数注入
) : ViewModel() {

    private val _carState = MutableStateFlow(CarState())
    val carState: StateFlow<CarState> = _carState.asStateFlow()

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
                val newValue = if (value == VehicleIgnitionState.ON) Toggle.On else Toggle.Off
                _carState.update { state ->
                    state.copy(carControlState = state.carControlState.copy(engineState = newValue))
                }
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.PARKING_BRAKE_AUTO_APPLY,
                null
            ) { value, _ ->
                val newValue = if (value as? Boolean == true) Toggle.On else Toggle.Off
                _carState.update {
                    state -> state.copy(carControlState = state.carControlState.copy(autoHoldState = newValue))
                }

            },
            CarService.PropertyCallback(VehiclePropertyIds.PARKING_BRAKE_ON, null) { value, _ ->
                val newValue = if (value as? Boolean == true) Toggle.On else Toggle.Off
                _carState.update { state ->
                    state.copy(carControlState = state.carControlState.copy(parkingBrakeState = newValue))
                }
            },
            CarService.PropertyCallback(
                HIGH_BEAM_LIGHTS_SWITCH,
                null
            ) { value, _ ->
                val newValue = if (value == 1) Toggle.On else Toggle.Off
                _carState.update {
                    state -> state.copy(carLightState = state.carLightState.copy(highBeamState = newValue))
                }
            },
            CarService.PropertyCallback(HAZARD_LIGHTS_SWITCH, null) { value, _ ->
                val newValue = if (value == 1) Toggle.On else Toggle.Off
                _carState.update { state ->
                    state.copy(carLightState = state.carLightState.copy(hazardLightsState = newValue))
                }
            },
            CarService.PropertyCallback(HEADLIGHTS_SWITCH, null) { value, _ ->
                val newValue = if (value == 1) Toggle.On else Toggle.Off
                _carState.update { state ->
                    state.copy(carLightState = state.carLightState.copy(headLightsState = newValue))
                }
            },
            CarService.PropertyCallback(
                VehiclePropertyIds.DOOR_LOCK,
                doorAreaIds
            ) { value, areaId ->
                doorStates[areaId] = if (value as? Boolean == true) Lock.Locked else Lock.Unlocked
                val newValue = doorStates.values.reduce { acc, b -> acc or b }
                _carState.update { state ->
                    state.copy(carLockState = newValue)
                }
            },
            CarService.PropertyCallback(
                HVAC_TEMPERATURE_SET,
                listOf(SEAT_ROW_1_LEFT, SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                if (areaId == SEAT_ROW_1_LEFT) {
                    _carState.update {
                        state -> state.copy(acBoxState = state.acBoxState.copy(driverTemperature = value as Float))
                    }
                }
                if (areaId == SEAT_ROW_1_RIGHT) {
                    _carState.update {
                        state -> state.copy(acBoxState = state.acBoxState.copy(coPilotTemperature = value as Float))
                    }
                }
            }
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }

    // 将 Toggle 转换为 Int
    private fun Toggle.toIntValue() = when (this) {
        Toggle.On -> 1
        Toggle.Off -> 0
    }

    fun dispatch(action: ViewAction) {
        _carState.update { reduce(carState.value, action) }
        sideEffect(carState.value, action)
    }

    private fun sideEffect(
        state: CarState,
        action: ViewAction
    ) {
        when (action) {
            is ViewAction.ToggleCarLock -> {
                val newValue = state.carLockState != Lock.Unlocked
                carService.setPropertyForMultipleAreas(
                    VehiclePropertyIds.DOOR_LOCK,
                    doorAreaIds,
                    newValue
                )
            }

            is ViewAction.ToggleHeadLights -> {
                carService.setProperty(
                    Int::class.java,
                    HEADLIGHTS_SWITCH,
                    VEHICLE_AREA_TYPE_GLOBAL,
                    state.carLightState.headLightsState.toIntValue()
                )
            }

            is ViewAction.ToggleHazardLights -> {
                carService.setProperty(
                    Int::class.java,
                    HAZARD_LIGHTS_SWITCH,
                    VEHICLE_AREA_TYPE_GLOBAL,
                    state.carLightState.hazardLightsState.toIntValue()
                )
            }

            is ViewAction.ToggleHighBeamLights -> {
                carService.setProperty(
                    Int::class.java,
                    HIGH_BEAM_LIGHTS_SWITCH,
                    VEHICLE_AREA_TYPE_GLOBAL,
                    state.carLightState.highBeamState.toIntValue()
                )
            }

            is ViewAction.OnSweepStep -> {
                if (action.temperatureType == TemperatureType.Driver) {
                    carService.setProperty(
                        HVAC_TEMPERATURE_SET,
                        SEAT_ROW_1_LEFT,
                        action.temperature
                    )
                } else {
                    carService.setProperty(
                        HVAC_TEMPERATURE_SET,
                        SEAT_ROW_1_RIGHT,
                        action.temperature
                    )
                }
            }
            else -> Unit
        }
    }

    private fun reduce(
        state: CarState,
        action: ViewAction,
    ): CarState {
        return when (action) {
            is ViewAction.ToggleCarLock -> {
                state.copy(carLockState = state.carLockState.switch())
            }
            is ViewAction.ToggleHeadLights -> {
                state.copy(carLightState = state.carLightState.copy(headLightsState = state.carLightState.headLightsState.toggle()))
            }
            is ViewAction.ToggleHazardLights -> {
                state.copy(carLightState = state.carLightState.copy(hazardLightsState = state.carLightState.hazardLightsState.toggle()))
            }
            is ViewAction.ToggleHighBeamLights -> {
                state.copy(carLightState = state.carLightState.copy(highBeamState = state.carLightState.highBeamState.toggle()))
            }
            is ViewAction.OnSweepStep -> {
                if (action.temperatureType == TemperatureType.Driver) {
                    state.copy(acBoxState = state.acBoxState.copy(driverTemperature = action.temperature))
                } else {
                    state.copy(acBoxState = state.acBoxState.copy(coPilotTemperature = action.temperature))
                }
            }
            else -> state
        }
    }

    override fun onCleared() {
        super.onCleared()
        carService.unregisterPropertyListeners(this.propertyCallbacks)
    }
}