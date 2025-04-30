package com.thoughtworks.carapp.ui.setting.viewmodel

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo
import com.thoughtworks.carapp.ui.setting.presentation.SeatControlUiState
import com.thoughtworks.carapp.ui.setting.presentation.SingleSeatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(private val carService: CarService) : ViewModel() {
    private val _operationState = MutableStateFlow(SeatControlUiState())
    val operationState: StateFlow<SeatControlUiState> = _operationState.asStateFlow()

    private var propertyCallbacks: List<CarService.PropertyCallback> = listOf()

    init {
        connectToCar()
    }

    private fun getLevelFromCarValue(type: SeatOperationInfo, value: Int): Int {
        if (type.values != null && value != 0) {
            return type.values.indexOf(value) + 1
        }
        return when {
            value <= 3 -> value
            value <= 20 -> 1
            value <= 40 -> 2
            else -> 3
        }
    }

    private fun connectToCar() {
        this.propertyCallbacks = listOf(
            // 座椅通风
            CarService.PropertyCallback(
                VehiclePropertyIds.HVAC_SEAT_VENTILATION,
                listOf(VehicleAreaSeat.SEAT_ROW_1_LEFT, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                updateSeatState(SeatOperationInfo.COOLING, areaId, value)
            },
            // 座椅加热
            CarService.PropertyCallback(
                VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
                listOf(VehicleAreaSeat.SEAT_ROW_1_LEFT, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                updateSeatState(SeatOperationInfo.HEATING, areaId, value)
            }
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }

    private fun updateSeatState(type: SeatOperationInfo, areaId: Int, value: Any) {
        val level = getLevelFromCarValue(type, value as Int)
        val areaSeat = AreaSeat.getByAreaId(areaId)
        if (areaSeat != null) {
            toggleOperationLevel(areaSeat, type, level)
        }
    }

    private fun updateSeatState(
        state: SingleSeatState,
        type: SeatOperationInfo,
        newLevel: Int
    ): SingleSeatState {
        return when (type) {
            SeatOperationInfo.HEATING -> state.copy(
                heatingLevel = newLevel,
            )

            SeatOperationInfo.COOLING -> state.copy(
                coolingLevel = newLevel,
            )

            SeatOperationInfo.MESSAGE -> state.copy(
                messageLevel = newLevel
            )
        }
    }

    fun toggleOperationLevel(areaSeat: AreaSeat, type: SeatOperationInfo, level: Int?) {
        _operationState.update { currentState ->
            val currentSeatState = currentState.getSeatState(areaSeat)
            val newLevel = level ?: ((currentSeatState.getLevelFor(type) + 1) % 4)
            val updatedSeatSeat = updateSeatState(currentSeatState, type, newLevel)

            when (areaSeat) {
                AreaSeat.DRIVER -> currentState.copy(driverSeat = updatedSeatSeat)
                AreaSeat.COPILOT -> currentState.copy(copilotSeat = updatedSeatSeat)
            }
        }
    }

    fun setSeatValue(seat: AreaSeat, type: SeatOperationInfo) {
        if (type.propertyId != null) {
            var level = (_operationState.value.getSeatState(seat).getLevelFor(type) + 1) % 4
            if (type.values != null && level != 0) {
                level = type.values[level - 1]
            }
            carService.setProperty(type.propertyId, seat.areaId, level)
        } else {
            toggleOperationLevel(seat, type, null)
        }
    }
}
